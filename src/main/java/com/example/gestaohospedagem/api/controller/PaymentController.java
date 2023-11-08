package com.example.gestaohospedagem.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestaohospedagem.api.dto.PaymentDTO;
import com.example.gestaohospedagem.model.entity.Payment;
import com.example.gestaohospedagem.model.entity.Reserve;
import com.example.gestaohospedagem.service.PaymentService;
import com.example.gestaohospedagem.service.ReserveService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService service;
	private final ReserveService reserveService;
	
	@PostMapping
	private ResponseEntity<Object> saveNewPayment(@RequestBody PaymentDTO dto) {
		try {
			Reserve reserve = reserveService.findById(dto.getId())
					.orElseThrow(() ->  new Exception("Usuário não encontrado para o ID informado!"));
			
			Payment payment = new Payment();
			payment.setReserve(reserve);
			payment.setValueWeekday(dto.getWeekday());
			payment.setValueWeekendays(dto.getWeekendays());
			payment.setValueGarageWeekendays(dto.getGarageWeekendays());
			payment.setValueGarageWeekday(dto.getGarageWeekday());		
			
			service.save(payment);
			
			return new ResponseEntity<Object>(payment, HttpStatus.CREATED);
		} catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	private ResponseEntity<Object> getPayment (@RequestParam(value = "id") Long id) {
		try {
			Reserve reserve = reserveService.findById(id)
					.orElseThrow(() ->  new Exception("Usuário não encontrado para o ID informado!"));
			
			Payment payment = service.getPayment(reserve);
			
			return ResponseEntity.ok(payment);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
