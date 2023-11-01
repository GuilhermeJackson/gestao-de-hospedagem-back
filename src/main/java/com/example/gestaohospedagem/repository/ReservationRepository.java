package com.example.gestaohospedagem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.gestaohospedagem.model.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	 List<Reservation> findByGuestId(Long guestId);
	 
	 @Query("SELECT r FROM Reservation r JOIN FETCH r.guest")
	    List<Reservation> findAllReservationsWithGuest();

	 @Query("SELECT r FROM Reservation r WHERE r.checkin IS NULL")
		List<Reservation> findAllWithoutCheckin();
}