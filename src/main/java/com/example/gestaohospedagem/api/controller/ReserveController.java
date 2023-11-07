package com.example.gestaohospedagem.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestaohospedagem.api.dto.ReservationDTO;
import com.example.gestaohospedagem.api.dto.ReservationSaveCheckinDTO;
import com.example.gestaohospedagem.model.entity.Guest;
import com.example.gestaohospedagem.model.entity.Reserve;
import com.example.gestaohospedagem.service.GuestService;
import com.example.gestaohospedagem.service.ReserveService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reserva")
@RequiredArgsConstructor
public class ReserveController {
	private final ReserveService service;
	private final GuestService guestService;
	
	@GetMapping
	public ResponseEntity<Object> getReserveById() {
	    try {
	    	List<Reserve> reserves = service.findAllReservationsWithGuest();
	        
	        if (reserves != null) {
	            return new ResponseEntity<>(reserves, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Reserva não encontrada", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
	
	@PostMapping
	private ResponseEntity<Object> createNewReserve(@RequestBody ReservationDTO dto) {
		try {
			Guest guest = guestService.findById(dto.getId_guest())
					.orElseThrow(() ->  new Exception("Usuário não encontrado para o ID informado!"));
			Reserve reserve = Reserve.builder()
					.prevCheckin(dto.getPrevCheckin())
					.prevCheckout(dto.getPrevCheckout())
					.isGarage(dto.isGarage())
					.guest(guest)
					.build();
			
			service.saveNewReserve(reserve);
			return new ResponseEntity<Object>(reserve, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/atendente/checkin")
	public ResponseEntity<Object> getReserveWithoutCheckin() {
		try {
			List<Reserve> reserves = service.findAllWithoutCheckinAndWithGuest();
			if (reserves != null) {
	            return new ResponseEntity<>(reserves, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Nenhuma hóspede pode dar o checkin", HttpStatus.NOT_FOUND);
	        }
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/atendente/checkout")
	public ResponseEntity<Object> getReserveWithoutCheckout() {
		try {
			List<Reserve> reserves = service.findAllWithoutCheckoutAndWithGuest();
			if (reserves != null) {
	            return new ResponseEntity<>(reserves, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Nenhuma hóspede pode dar o checkout", HttpStatus.NOT_FOUND);
	        }
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/atendente/checkin")
	private ResponseEntity<Object> saveCheckinGuest(@RequestBody ReservationSaveCheckinDTO dto) {
		try {
			Reserve reserve = service.findById(dto.getId())
					.orElseThrow(() ->  new Exception("Usuário não encontrado para o ID informado!"));
			
			LocalDateTime dateNow = LocalDateTime.now();
			reserve.setCheckin(dateNow);
			
			service.saveCheckin(reserve);
			return new ResponseEntity<Object>(reserve, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/atendente/checkout")
	private ResponseEntity<Object> saveCheckoutGuest(@RequestBody ReservationSaveCheckinDTO dto) {
		try {
			Reserve reserve = service.findById(dto.getId())
					.orElseThrow(() ->  new Exception("Usuário não encontrado para o ID informado!"));
			
			LocalDateTime dateNow = LocalDateTime.now();
			
			if(reserve.getCheckin() != null && reserve.getCheckout() == null) {
				reserve.setCheckout(dateNow);
			}
			
			service.saveNewReserve(reserve);
			return new ResponseEntity<Object>(reserve, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
