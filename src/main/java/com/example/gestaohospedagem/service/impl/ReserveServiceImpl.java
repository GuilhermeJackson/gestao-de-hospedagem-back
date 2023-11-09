package com.example.gestaohospedagem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestaohospedagem.exception.ReserveException;
import com.example.gestaohospedagem.model.entity.Reserve;
import com.example.gestaohospedagem.repository.ReserveRepository;
import com.example.gestaohospedagem.service.ReserveService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {
	@Autowired
	private ReserveRepository repository;

	@Override
	public Reserve saveNewReserve(Reserve reserve) {
		if(reserve == null) {
			throw new ReserveException("Reserva está vazia!");
		}
		return repository.save(reserve);
	}

	@Override
	public Optional<Reserve> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Reserve> findAllReservationsWithGuest() {
		List<Reserve> reserves = repository.findAllReservationsWithGuest();
		if(reserves.isEmpty()) {
			throw new ReserveException("Lista de hópede vazia!");
		}
		 return reserves;
	}

	@Override
	public List<Reserve> findAllWithoutCheckinAndWithGuest() {
		List<Reserve> reserves = repository.findAllWithoutCheckinAndWithGuest();
		if(reserves.isEmpty()) {
			throw new ReserveException("Lista de hópede vazia!");
		}
		return reserves;
	}

	@Override
	public List<Reserve> findAllWithoutCheckoutAndWithGuest() {
		List<Reserve> reserves = repository.findAllWithoutCheckoutAndWithGuest();
		
		return reserves;
	}

	@Override
	public Reserve saveCheckin(Reserve reserve) {
		validateCheckin(reserve);
		return repository.save(reserve);
	}

	@Override
	public Reserve saveCheckout(Reserve reserve) {
		validateCheckout(reserve);
		return repository.save(reserve);
	}
	
	private void validateCheckin(Reserve reserve) {
		if(reserve.getCheckin() != null && reserve.getCheckout() != null) {
			throw new ReserveException("Hóspede já tem um checkin realizado em uma reserva");
		}
		if(reserve.getCheckin().isBefore(reserve.getPrevCheckin())) {
			throw new ReserveException("Hóspede não pode entrar antes da data de reserva!");
		}
		if(reserve.getCheckin().isBefore(reserve.getPrevCheckin()) &&
				reserve.getCheckin().getHour() <= 14) {
			throw new ReserveException(
					"Checkin fora de hora. Hóspede podera dar o checkin depois das 14h"
					);
		}
	}
	
	private void validateCheckout(Reserve reserve) {
		if(reserve.getCheckin() != null) {
			throw new ReserveException("É preciso fazer checkin para realizar o checkout");
		}
		if(reserve.getCheckin().isBefore(reserve.getCheckout())) {
			throw new ReserveException("Data de checkout incorreta");
		}
	}
}
