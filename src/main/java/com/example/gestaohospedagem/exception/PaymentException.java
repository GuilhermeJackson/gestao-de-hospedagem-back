package com.example.gestaohospedagem.exception;

@SuppressWarnings("serial")
public class PaymentException extends RuntimeException{
	public PaymentException(String msg) {
		super(msg);
	}
}
