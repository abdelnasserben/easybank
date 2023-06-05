package com.dabel.easybank.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentViewDTO extends PaymentDTO{

	private int userId;
}
