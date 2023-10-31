package com.example.gestaohospedagem.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestaohospedagem.api.dto.GuestDTO;
import com.example.gestaohospedagem.model.entity.Guest;
import com.example.gestaohospedagem.service.GuestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hospede")
@RequiredArgsConstructor
public class GuestController {
	
	private final GuestService guestService;
	
	@GetMapping
	public ResponseEntity<Object> searchGuest(
			@RequestParam(value = "name", required=false) String name,
			@RequestParam(value = "phone", required=false) String phone,
			@RequestParam(value = "cpf", required=false) String cpf
			) {
		try {
			Guest guestFilter = new Guest();
			guestFilter.setName(name);
			guestFilter.setPhone(phone);
			guestFilter.setCpf(cpf);
			
			List<Guest> guests = guestService.find(guestFilter);
			
			return ResponseEntity.ok(guests);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	
	@PostMapping
	private ResponseEntity<Object> cadastrarHospede(@RequestBody GuestDTO dto ) {
		try {
			Guest guest = Guest.builder()
					.name(dto.getName())
					.cpf(dto.getCpf())
					.phone(dto.getPhone())
					.build();
			
			guestService.salvar(guest);
			
			return new ResponseEntity<Object>(guest, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
//	@GetMapping
//	private ResponseEntity listGuest() {
//		try {
//			List<Guest> listGuest = guestService.getListGuest();
//			return ResponseEntity.ok(listGuest);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
}
