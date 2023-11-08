package com.example.gestaohospedagem.service;

import com.example.gestaohospedagem.model.entity.Payment;
import com.example.gestaohospedagem.model.entity.Reserve;

public interface PaymentService {
	Payment save(Payment payment);
	
	Payment getPayment(Reserve reserve);
}
