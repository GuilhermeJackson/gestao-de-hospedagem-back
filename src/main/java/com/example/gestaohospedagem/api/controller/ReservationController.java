package com.example.gestaohospedagem.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	private final ReservationService service;
	private final GuestService guestService;
	
	@GetMapping
	public ResponseEntity<Object> getReserveById() {
	    try {
	    	List<Reservation> reservations = service.findAllReservationsWithGuest();
	        
	        if (reservations != null) {
	            return new ResponseEntity<>(reservations, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Reserva não encontrada", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
	
	@GetMapping("/atendente")
	public ResponseEntity<Object> getReserveWithoutCheckin() {
		try {
			List<Reservation> reservations = service.findAllReservationsWithGuest();
			if (reservations != null) {
	            return new ResponseEntity<>(reservations, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Nenhuma hóspede pode dar o checkin", HttpStatus.NOT_FOUND);
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
					.prevCheckin(dto.getPrevCheckin())
					.prevCheckout(dto.getPrevCheckout())
					.isGarage(dto.isGarage())
					.guest(guest)
					.build();
			
			service.salvar(reservation);
			return new ResponseEntity<Object>(reservation, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
