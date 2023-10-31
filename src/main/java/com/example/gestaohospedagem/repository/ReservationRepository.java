package com.example.gestaohospedagem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestaohospedagem.model.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	 List<Reservation> findByGuestId(Long guestId);
	}
