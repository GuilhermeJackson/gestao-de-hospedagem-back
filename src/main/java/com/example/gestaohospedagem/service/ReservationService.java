package com.example.gestaohospedagem.service;

import java.util.List;
import java.util.Optional;

import com.example.gestaohospedagem.model.entity.Reservation;

public interface ReservationService {
	Reservation salvar(Reservation reservation);
	
	Optional<Reservation> findById(Long id);
	
	List<Reservation> findAllReservationsWithGuest();
}
