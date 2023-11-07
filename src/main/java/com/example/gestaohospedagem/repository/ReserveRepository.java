package com.example.gestaohospedagem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.gestaohospedagem.model.entity.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
	 List<Reserve> findByGuestId(Long guestId);
	 
	 @Query("SELECT r FROM Reserve r JOIN FETCH r.guest")
	    List<Reserve> findAllReservationsWithGuest();
	 
	 @Query("SELECT r FROM Reserve r LEFT JOIN FETCH r.guest WHERE r.checkin IS NULL")
		List<Reserve> findAllWithoutCheckinAndWithGuest();

	 @Query("SELECT r FROM Reserve r LEFT JOIN FETCH r.guest WHERE r.checkout IS NULL")
		List<Reserve> findAllWithoutCheckoutAndWithGuest();
}