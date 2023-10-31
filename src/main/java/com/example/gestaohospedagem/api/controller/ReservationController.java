package com.example.gestaohospedagem.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestaohospedagem.api.dto.ReservationDTO;
import com.example.gestaohospedagem.model.entity.Guest;
import com.example.gestaohospedagem.model.entity.Reservation;
import com.example.gestaohospedagem.service.GuestService;
import com.example.gestaohospedagem.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reserva")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;
	private final GuestService guestService;
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getReserveById(@PathVariable Long id) {
	    try {
	        List<Reservation> reservations = reservationService.findByGuestId(id);
	        
	        if (reservations != null) {
	            return new ResponseEntity<>(reservations, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Reserva não encontrada", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}

	
	@PostMapping
	private ResponseEntity<Object> salvar(@RequestBody ReservationDTO dto) {
		try {
			Guest guest = guestService.findById(dto.getId_guest())
					.orElseThrow(() ->  new Exception("Usuário não encontrado para o ID informado!"));
			Reservation reservation = Reservation.builder()
					.checkin(dto.getCheckin())
					.checkout(dto.getCheckout())
					.guest(guest)
					.build();
			
			reservationService.salvar(reservation);
			return new ResponseEntity<Object>(reservation, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
