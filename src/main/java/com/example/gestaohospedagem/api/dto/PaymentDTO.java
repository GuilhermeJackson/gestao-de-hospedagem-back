package com.example.gestaohospedagem.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentDTO {
	private Long id;
	private int weekday;
	private int weekendays;
	private int garageWeekday;
	private int garageWeekendays;
	private BigDecimal totalValue;
}
