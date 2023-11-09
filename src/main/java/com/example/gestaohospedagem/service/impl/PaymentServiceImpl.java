package com.example.gestaohospedagem.service.impl;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestaohospedagem.exception.PaymentException;
import com.example.gestaohospedagem.model.entity.Payment;
import com.example.gestaohospedagem.model.entity.Reserve;
import com.example.gestaohospedagem.repository.PaymentRepository;
import com.example.gestaohospedagem.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private PaymentRepository repository;
	
	@Override
	public Payment save(Payment payment) {
		if(payment.getReserve().getCheckout() != null) {
			throw new PaymentException
			("Não é possível realizar o pagamento. Hóspede já deu checkout!");
		}
		return repository.save(payment);
	}

	@Override
	public Payment getPayment(Reserve reserve) {
		Payment payment = new Payment();
		
		LocalDateTime currentDate = LocalDateTime.now();
		int weekdays = 0;
        int weekends = 0;
        
	     while (!currentDate.isAfter(reserve.getCheckin())) {
	            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
	                weekends++;
	            } else {
	                weekdays++;
	            }
	            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
	     }
	     
	     int valueGarageWeekdays = 0;
	     int valueGarageWeekends = 0;
	     if(reserve.isGarage() == true) {
	    	 valueGarageWeekdays = weekdays * 15;
	    	 valueGarageWeekends = weekends * 20;
	     }
	     
	     int valueWeekdays = (weekdays * 120);
	     int valueWeekends = weekends * 180;
	     
//	     BigDecimal totalValue = BigDecimal.valueOf(valueWeekdays + valueWeekends + valueGarageWeekdays + valueGarageWeekends);
	     
	     BigDecimal totalValue = BigDecimal.valueOf(valueWeekdays)
                 .add(BigDecimal.valueOf(valueWeekends))
                 .add(BigDecimal.valueOf(valueGarageWeekdays))
                 .add(BigDecimal.valueOf(valueGarageWeekends));
	     
	     payment.setValueWeekday(valueWeekdays);
	     payment.setValueWeekendays(valueWeekends);
	     payment.setValueGarageWeekday(valueGarageWeekdays);
	     payment.setValueGarageWeekendays(valueGarageWeekends);
	     payment.setQtWeekday(weekdays);
	     payment.setQtWeekendays(weekends);
	     payment.setReserve(reserve);
	     payment.setTotalValue(totalValue);
	     
		return payment;
	}
	
	
}
