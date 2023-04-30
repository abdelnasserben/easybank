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
		
		//TODO: get all user accounts
		List<Account> userAccounts = accountService.getAllAccountByUserId(user.getUserId());
		
		//TODO: retrieves the total amount of the user's accounts
		double totalBalance = accountService.totalBalance(user.getUserId());
		
		
		model.addAttribute("user", this.user);
		model.addAttribute("userAccounts", userAccounts);
		model.addAttribute("totalBalance", totalBalance);
		
		
		return "dashboard";
	}
	
	@GetMapping("/app/payments")
	private String paymensHistory(Model model) {
		
		this.user = this.setUser();
		
		//TODO: retrieves all the payment history of the user's accounts
		List<PaymentView> paymentsHistory = pViewService.findAll(this.user.getUserId());
		
		model.addAttribute("user", this.user);
		model.addAttribute("paymentsHistory", paymentsHistory);
	
		return "payments";
	}
	
	
	@GetMapping("/app/transactions")
	private String transactionsHistory(Model model) {
		
		this.user = this.setUser();
		
		//TODO: retrieves all the transaction history of the user's accounts
		List<TransactionView> transactionsHistory = trViewService.findAll(this.user.getUserId());
		
		model.addAttribute("user", this.user);
		model.addAttribute("transactionsHistory", transactionsHistory);
	
		return "transactions";
	}
	
	
	@PostMapping("/account")
	public String saveAccount(@Valid Account account, BindingResult binding, RedirectAttributes redirect) {
		
		//TODO: check if there are no empty values
		if(binding.hasErrors()) {
			
			String message = "Invalid account name and type!";
			return redirectWithMessage(message, redirect, true);
		}
		
		//TODO: generate an account name
		String accountNumber = Helper.generateAccountNumber();
		
		//TODO: update some account data
		account.setUserId(user.getUserId());
		account.setAccountNumber(accountNumber);
		account.setAccountType(account.getAccountTypeEnum().name());
		
		if(accountService.exists(account)) {
			
			String message = "Account name already exists!";
			return redirectWithMessage(message, redirect, true);
		}
		
		//TODO: save account
		accountService.save(account);
		
		//TODO: redirect
		String message = "Account has been created!";
		return redirectWithMessage(message, redirect, false);
	}
	
	
	
	
	
	@PostMapping("/deposit")
	public String deposit(@RequestParam(value = "amount") String amount,
						@RequestParam(value = "accountName") String accountName,
						RedirectAttributes redirect) {
		
		//TODO: check if there are no empty values
		if(amount.isBlank() || Double.valueOf(amount) <= 0 || accountName.isBlank()) {
			
			String message = "Invalid amount and Account name!";
			return redirectWithMessage(message, redirect, true);
		}
		
		//TODO: try to get an account by name
		Optional<Account> checkAccount = accountService.findByAccountNumber(accountName);
		
		//TODO: check if it exists
		if(!checkAccount.isPresent()) {
			
			String message = "The account does not exist!";
			return redirectWithMessage(message, redirect, true);
		}
		
		//TODO: retrieves the optional account
		Account account = checkAccount.get();
				
		//TODO: change amount type and account balance
		Double amountCast = Double.valueOf(amount);
		account.setBalance(account.getBalance() + amountCast);
		
		//TODO: save account
		accountService.save(account);
		
		//TODO: save the transaction
		Transaction transaction = new Transaction(
				account.getAccountId(), 
				TransactionProvider.Type.DEPOSIT,
				amountCast,
				TransactionProvider.Source.ONLINE,
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.FUNDING_ACCOUNT
				);
		
		transactionService.save(transaction);
		
		
		//TODO: redirecting
		String message = "Deposit well made!";
		return redirectWithMessage(message, redirect, false);
	}
	
	@PostMapping("/withdraw")
	public String withdraw(@RequestParam(value = "amount") String amount,
						@RequestParam(value = "accountName") String accountName,
						RedirectAttributes redirect) {
		
		//TODO: check if there are no empty values
		if(amount.isBlank() || Double.valueOf(amount) <= 0 || accountName.isBlank()) {
			
			String message = "Invalid amount and Account name";
			return redirectWithMessage(message, redirect, true);
		}
		
		//TODO: try to get an account by name
		Optional<Account> checkAccount = accountService.findByAccountNumber(accountName);
		
		//TODO: check if it exists
		if(!checkAccount.isPresent()) {
			
			String message = "The account does not exist";
			return redirectWithMessage(message, redirect, true);
		}
		
		//TODO: change amount type
		Double amountCast = Double.valueOf(amount);
		
		//TODO: retrieves the optional account
		Account account = checkAccount.get();
		
		//TODO: check if the account amount is sufficient
		if(account.getBalance() <= amountCast) {
			
			//TODO: save failed transaction
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
			String message = "The amount of the account is insufficient!";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		//TODO: Subtract the account amount and save account
		account.setBalance(account.getBalance() - amountCast);
		accountService.save(account);
		
		
		//TODO: save the transaction
		Transaction transaction = new Transaction(
				account.getAccountId(), 
				TransactionProvider.Type.WITHDRAW,
				amountCast,
				TransactionProvider.Source.ONLINE,
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.CUSTOMER_WITHDRAWAL
				);
		
		transactionService.save(transaction);
		
		//TODO: redirecting
		String message = "Withdrawal well done!";
		return redirectWithMessage(message, redirect, false);
	}
	
	@PostMapping("/transfer")
	public String transfer(@RequestParam(value = "amount") String amount,
						@RequestParam(value = "debitAccountName") String debitAccountName,
						@RequestParam(value = "creditAccountName") String creditAccountName,
						RedirectAttributes redirect) {
		
		
		//TODO: check if there are no empty values
		if(amount.isBlank() || Double.valueOf(amount) <= 0) {
			
			String message = "Invalid amount";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		//TODO: check if the selected accounts are different
		if(debitAccountName.equalsIgnoreCase(creditAccountName)) {
			
			String message = "Please choose two different accounts!";
			return redirectWithMessage(message, redirect, true);
		}
		
		//TODO: try retrieves debit and credit account
		Optional<Account> checkDebitAccount = accountService.findByAccountNumber(debitAccountName);
		Optional<Account> checkCreditAccount = accountService.findByAccountNumber(creditAccountName);
		
		//TODO: check the existence of both accounts
		if(!checkDebitAccount.isPresent() || !checkCreditAccount.isPresent()) {
			
			String message = "Invalid accounts!";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		//TODO: change amount type
		Double amountCast = Double.valueOf(amount);
		
		//TODO: retrieves the optional accounts
		Account debitAccount = checkDebitAccount.get();
		Account creditAccount = checkCreditAccount.get();
		
		//TODO: check if the debit account amount is sufficient
		if(debitAccount.getBalance() <= amountCast) {
			
			//TODO: save failed transaction
			Transaction failedTransaction = new Transaction(
					debitAccount.getAccountId(), 
					TransactionProvider.Type.TRANSFER,
					amountCast,
					TransactionProvider.Source.ONLINE,
					TransactionProvider.Status.FAILED,
					TransactionProvider.ReasonCode.INSUFFICIENT_FUNDS
					);
			
			transactionService.save(failedTransaction);
			
			
			//TODO: redirecting
			String message = "The amount of the " + debitAccount.getAccountName() + " account is insufficient";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		//TODO: update account balances
		debitAccount.setBalance(debitAccount.getBalance() - amountCast);
		creditAccount.setBalance(creditAccount.getBalance() + amountCast);
		
		
		//TODO: save the accounts with their new balances
		accountService.save(debitAccount);
		accountService.save(creditAccount);
		
		
		//TODO: save their respective transactions
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
		
		transactionService.save(debitTransaction);
		transactionService.save(creditTransaction);
		
		
		//TODO: redirecting
		String message = "Transfer well done!";
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
		
		
		//TODO: check if there are no empty values
		if(amount.isBlank() || Double.valueOf(amount) <= 0 || beneficiary.isBlank()
							|| reference.isBlank()) {
			
			String message = "All fields are mandatory!";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		//TODO: try to get an account by name
		Optional<Account> checkAccount = accountService.findByAccountNumber(debitAccount);
		
		
		//TODO: check if exists account
		if(!checkAccount.isPresent()) {
			
			String message = "Account does not exist!";
			return redirectWithMessage(message, redirect, true);
		}
		
		//TODO: change amount type
		Double amountCast = Double.valueOf(amount);
		
		
		//TODO: retrieves the optional account
		Account account = checkAccount.get();
		
		if(account.getBalance() <= amountCast) {
			
			//TODO: save failed transaction
			Transaction failedTransaction = new Transaction(
					account.getAccountId(), 
					TransactionProvider.Type.PAYMENT,
					amountCast,
					TransactionProvider.Source.ONLINE,
					TransactionProvider.Status.FAILED,
					TransactionProvider.ReasonCode.INSUFFICIENT_FUNDS
					);
			
			transactionService.save(failedTransaction);
			
			//TODO: save failed payment too
			Payment failedPayment = new Payment(
					account.getAccountId(), 
					beneficiary, 
					beneficiaryAccountNumber, 
					amountCast, 
					reference, 
					TransactionProvider.Status.FAILED,
					TransactionProvider.ReasonCode.PAY_CUSTOMER);
			
			paymentService.save(failedPayment);
			
			//TODO: redirecting
			String message = "The amount of the account is insufficient!";
			return redirectWithMessage(message, redirect, true);
		}
		
		
		//TODO : Update current account balance and saving account
		account.setBalance(account.getBalance() - amountCast);
		accountService.save(account);
		
		//TODO: save the transaction
		Transaction transaction = new Transaction(
				account.getAccountId(), 
				TransactionProvider.Type.PAYMENT,
				amountCast,
				TransactionProvider.Source.ONLINE,
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.PAY_CUSTOMER
				);
		
		transactionService.save(transaction);
		
		
		//TODO: save payment too
		Payment payment = new Payment(
				account.getAccountId(), 
				beneficiary, 
				beneficiaryAccountNumber, 
				amountCast, 
				reference, 
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.PAY_CUSTOMER);
		
		paymentService.save(payment);
		
		
		//TODO: redirecting
		String message = "Payment well done!";
		return redirectWithMessage(message, redirect, false);
	}
	
	private User setUser() {
		
		//TODO: set the user with the one who is connected
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.getByEmail(auth.getName()).get();
	}
	
	
	private String redirectWithMessage(String message, RedirectAttributes redirect, boolean isError) {
		
		String key = isError == true ? "errorMessage" : "successMessage";
	
		redirect.addFlashAttribute(key, message);
		return "redirect:/app";
	}

}
