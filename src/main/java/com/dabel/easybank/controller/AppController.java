package com.dabel.easybank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dabel.easybank.helper.Helper;
import com.dabel.easybank.helper.TransactionProvider;
import com.dabel.easybank.model.Account;
import com.dabel.easybank.model.Payment;
import com.dabel.easybank.model.PaymentView;
import com.dabel.easybank.model.Transaction;
import com.dabel.easybank.model.TransactionView;
import com.dabel.easybank.model.User;
import com.dabel.easybank.service.AccountService;
import com.dabel.easybank.service.PaymentService;
import com.dabel.easybank.service.PaymentViewService;
import com.dabel.easybank.service.TransactionService;
import com.dabel.easybank.service.TransactionViewService;
import com.dabel.easybank.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AppController {
	
	private User user;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private PaymentViewService pViewService;
	
	@Autowired
	private TransactionViewService trViewService;
	
	
	@GetMapping("/app")
	public String dashboard(Account account, Model model) {
		
		this.user = this.setUser();
		
		List<Account> userAccounts = accountService.getAllAccountByUserId(user.getUserId());
		
		double totalBalance = accountService.totalBalance(user.getUserId());
		
		model.addAttribute("user", this.user);
		model.addAttribute("userAccounts", userAccounts);
		model.addAttribute("totalBalance", totalBalance);
		
		
		return "dashboard";
	}
	
	@GetMapping("/app/payments")
	private String paymensHistory(Model model) {
		
		this.user = this.setUser();
		
		List<PaymentView> paymentsHistory = pViewService.findAll(this.user.getUserId());
		
		model.addAttribute("user", this.user);
		model.addAttribute("paymentsHistory", paymentsHistory);
	
		return "payments";
	}
	
	
	@GetMapping("/app/transactions")
	private String transactionsHistory(Model model) {
		
		this.user = this.setUser();
		
		List<TransactionView> transactionsHistory = trViewService.findAll(this.user.getUserId());
		
		model.addAttribute("user", this.user);
		model.addAttribute("transactionsHistory", transactionsHistory);
	
		return "transactions";
	}
	
	
	@PostMapping("/account")
	public String saveAccount(@Valid Account account, BindingResult binding, RedirectAttributes redirect) {
		
		if(binding.hasErrors()) {
			
			String message = "Nom et type de compte invalides !";
			return redirectWithMessage(message, redirect, true);
		}
		
		//update important informations of an account
		String accountNumber = Helper.generateAccountNumber();
		
		account.setUserId(user.getUserId());
		account.setAccountNumber(accountNumber);
		account.setAccountType(account.getAccountTypeEnum().name());
		
		if(accountService.exists(account)) {
			
			String message = "Un nom de compte existe déjà !";
			return redirectWithMessage(message, redirect, true);
		}
		
		//saving account
		accountService.save(account);
		
		//redirect message attribute
		String message = "Le compte a bien été créé !";
		return redirectWithMessage(message, redirect, false);
	}
	
	
	
	
	
	@PostMapping("/deposit")
	public String deposit(@RequestParam(value = "amount") String amount,
						@RequestParam(value = "accountName") String accountName,
						RedirectAttributes redirect) {
		
		if(amount.isBlank() || Double.valueOf(amount) <= 0 || accountName.isBlank()) {
			
			String message = "Invalid amount and Account name";
			return redirectWithMessage(message, redirect, true);
		}
		
		Optional<Account> checkAccount = accountService.findByAccountNumber(accountName);
		
		if(!checkAccount.isPresent()) {
			
			String message = "Le compte n'existe pas";
			return redirectWithMessage(message, redirect, true);
		}
		
		Double amountCast = Double.valueOf(amount);
		
		//we get the account and change his balance
		Account account = checkAccount.get();
		account.setBalance(account.getBalance() + amountCast);
		accountService.save(account);
		
		//we save the transaction too
		Transaction transaction = new Transaction(
				account.getAccountId(), 
				TransactionProvider.Type.DEPOSIT,
				amountCast,
				TransactionProvider.Source.ONLINE,
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.FUNDING_ACCOUNT
				);
		
		transactionService.save(transaction);
		
		//redirecting
		String message = "Dépôt bien effectué !";
		return redirectWithMessage(message, redirect, false);
	}
	
	@PostMapping("/withdraw")
	public String withdraw(@RequestParam(value = "amount") String amount,
						@RequestParam(value = "accountName") String accountName,
						RedirectAttributes redirect) {
		
		if(amount.isBlank() || Double.valueOf(amount) <= 0 || accountName.isBlank()) {
			
			String message = "Invalid amount and Account name";
			return redirectWithMessage(message, redirect, true);
		}
		
		Optional<Account> checkAccount = accountService.findByAccountNumber(accountName);
		
		if(!checkAccount.isPresent()) {
			
			String message = "Le compte n'existe pas";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		Double amountCast = Double.valueOf(amount);
		Account account = checkAccount.get();
		
		if(account.getBalance() <= amountCast) {
			
			//TODO create failed transaction
			Transaction failedTransaction = new Transaction(
					account.getAccountId(), 
					TransactionProvider.Type.WITHDRAW,
					amountCast,
					TransactionProvider.Source.ONLINE,
					TransactionProvider.Status.FAILED,
					TransactionProvider.ReasonCode.INSUFFICIENT_FUNDS
					);
			
			transactionService.save(failedTransaction);
			
			//TODO redirecting
			String message = "Le montant du compte est insuffisant";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		account.setBalance(account.getBalance() - amountCast);
		accountService.save(account);
		
		//we save the transaction too
		Transaction transaction = new Transaction(
				account.getAccountId(), 
				TransactionProvider.Type.WITHDRAW,
				amountCast,
				TransactionProvider.Source.ONLINE,
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.CUSTOMER_WITHDRAWAL
				);
		
		transactionService.save(transaction);
		
		//redirecting
		String message = "Retrait bien effectué !";
		return redirectWithMessage(message, redirect, false);
	}
	
	@PostMapping("/transfer")
	public String transfer(@RequestParam(value = "amount") String amount,
						@RequestParam(value = "debitAccountName") String debitAccountName,
						@RequestParam(value = "creditAccountName") String creditAccountName,
						RedirectAttributes redirect) {
		
		//check if no empty values
		if(amount.isBlank() || Double.valueOf(amount) <= 0) {
			
			String message = "Invalid amount";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		//check it's two different accounts
		if(debitAccountName.equalsIgnoreCase(creditAccountName)) {
			
			String message = "Veuillez chosir deux comptes distincts !";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		Optional<Account> checkDebitAccount = accountService.findByAccountNumber(debitAccountName);
		Optional<Account> checkCreditAccount = accountService.findByAccountNumber(creditAccountName);
		
		//check existing accounts in DB
		if(!checkDebitAccount.isPresent() || !checkCreditAccount.isPresent()) {
			
			String message = "Comptes invalides !";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		Double amountCast = Double.valueOf(amount);
		Account debitAccount = checkDebitAccount.get();
		Account creditAccount = checkCreditAccount.get();
		
		//check insufficient balance of account
		if(debitAccount.getBalance() <= amountCast) {
			
			//TODO create failed transaction
			Transaction failedTransaction = new Transaction(
					debitAccount.getAccountId(), 
					TransactionProvider.Type.TRANSFER,
					amountCast,
					TransactionProvider.Source.ONLINE,
					TransactionProvider.Status.FAILED,
					TransactionProvider.ReasonCode.INSUFFICIENT_FUNDS
					);
			
			transactionService.save(failedTransaction);
			
			//TODO redirecting
			String message = "Le montant du compte " + debitAccount.getAccountName() + " est insuffisant";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		//update accounts balances
		debitAccount.setBalance(debitAccount.getBalance() - amountCast);
		creditAccount.setBalance(creditAccount.getBalance() + amountCast);
		
		//saving new balances
		accountService.save(debitAccount);
		accountService.save(creditAccount);
		
		//create two transactions too for saving
		Transaction debitTransaction = new Transaction(
				debitAccount.getAccountId(), 
				TransactionProvider.Type.TRANSFER,
				amountCast,
				TransactionProvider.Source.ONLINE,
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.SUPPLY
				);
		
		Transaction creditTransaction = new Transaction(
				creditAccount.getAccountId(), 
				TransactionProvider.Type.TRANSFER,
				amountCast,
				TransactionProvider.Source.ONLINE,
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.SUPPLIED
				);
		
		//saving transactions
		transactionService.save(debitTransaction);
		transactionService.save(creditTransaction);
		
		//redirecting
		String message = "Transfert bien effectué !";
		return redirectWithMessage(message, redirect, false);
	}
	
	@PostMapping("/payment")
	public String payment(
						@RequestParam(value = "beneficiary") String beneficiary,
						@RequestParam(value = "beneficiaryAccountNumber") String beneficiaryAccountNumber,
						@RequestParam(value = "debitAccount") String debitAccount,
						@RequestParam(value = "reference") String reference,
						@RequestParam(value = "amount") String amount,
						RedirectAttributes redirect) {
		
		if(amount.isBlank() || Double.valueOf(amount) <= 0 || beneficiary.isBlank()
							|| reference.isBlank()) {
			
			String message = "Tous les champs sont obligatoires";
			return redirectWithMessage(message, redirect, true);
		}
		
		Optional<Account> checkAccount = accountService.findByAccountNumber(debitAccount);
		
		if(!checkAccount.isPresent()) {
			
			String message = "Le compte n'existe pas";
			return redirectWithMessage(message, redirect, true);
		}
		
		//TODO : Parsing values
		Double amountCast = Double.valueOf(amount);
		Account account = checkAccount.get();
		
		if(account.getBalance() <= amountCast) {
			
			//TODO create failed transaction
			Transaction failedTransaction = new Transaction(
					account.getAccountId(), 
					TransactionProvider.Type.PAYMENT,
					amountCast,
					TransactionProvider.Source.ONLINE,
					TransactionProvider.Status.FAILED,
					TransactionProvider.ReasonCode.INSUFFICIENT_FUNDS
					);
			
			transactionService.save(failedTransaction);
			
			//TODO : Create a failed payment
			Payment failedPayment = new Payment(
					account.getAccountId(), 
					beneficiary, 
					beneficiaryAccountNumber, 
					amountCast, 
					reference, 
					TransactionProvider.Status.FAILED,
					TransactionProvider.ReasonCode.PAY_CUSTOMER);
			
			paymentService.save(failedPayment);
			
			//TODO redirecting
			String message = "Le montant du compte est insuffisant";
			return redirectWithMessage(message, redirect, true);
		}
		
		//TODO : Update current account balance and saving account
		account.setBalance(account.getBalance() - amountCast);
		accountService.save(account);
		
		//TODO : Create a transaction and saving
		Transaction transaction = new Transaction(
				account.getAccountId(), 
				TransactionProvider.Type.PAYMENT,
				amountCast,
				TransactionProvider.Source.ONLINE,
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.PAY_CUSTOMER
				);
		
		transactionService.save(transaction);
		
		
		//TODO : Create a payment for payments history
		Payment payment = new Payment(
				account.getAccountId(), 
				beneficiary, 
				beneficiaryAccountNumber, 
				amountCast, 
				reference, 
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.PAY_CUSTOMER);
		
		paymentService.save(payment);
		
		
		//redirecting
		String message = "Payment bien effectué !";
		return redirectWithMessage(message, redirect, false);
	}
	
	private User setUser() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.getByEmail(auth.getName()).get();
	}
	
	
	private String redirectWithMessage(String message, RedirectAttributes redirect, boolean isError) {
		
		String key = isError == true ? "errorMessage" : "successMessage";
	
		redirect.addFlashAttribute(key, message);
		return "redirect:/app";
	}

}
