package com.example.gestaohospedagem.service;

import java.util.List;
import java.util.Optional;

import com.example.gestaohospedagem.model.entity.Reserve;

public interface ReserveService {
	Reserve saveNewReserve(Reserve reserve);
	
	Reserve saveCheckin(Reserve reserve);
	
	Reserve saveCheckout(Reserve reserve);
	
	Optional<Reserve> findById(Long id);
	
	List<Reserve> findAllReservationsWithGuest();
	
	List<Reserve> findAllWithoutCheckinAndWithGuest();
	
	List<Reserve> findAllWithoutCheckoutAndWithGuest();
}
