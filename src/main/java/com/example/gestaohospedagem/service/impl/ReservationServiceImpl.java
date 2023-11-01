package com.example.gestaohospedagem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestaohospedagem.model.entity.Reservation;
import com.example.gestaohospedagem.repository.ReservationRepository;
import com.example.gestaohospedagem.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
	@Autowired
	private ReservationRepository repository;

	@Override
	public Reservation salvar(Reservation reservation) {
		if(reservation == null) {
			throw new RuntimeException("Reserva está vazia!");
		}
		return repository.save(reservation);
	}

	@Override
	public Optional<Reservation> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Reservation> findAllReservationsWithGuest() {
		 return repository.findAllReservationsWithGuest();
	}

	@Override
	public List<Reservation> findAllWithoutCheckin() {
		return repository.findAllWithoutCheckin();
	}
}
