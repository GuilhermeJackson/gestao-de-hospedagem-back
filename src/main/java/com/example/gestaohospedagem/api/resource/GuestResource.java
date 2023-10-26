package com.example.gestaohospedagem.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestaohospedagem.api.dto.GuestDTO;
import com.example.gestaohospedagem.model.entity.Guest;
import com.example.gestaohospedagem.model.enums.Status;
import com.example.gestaohospedagem.service.GuestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hospede")
@RequiredArgsConstructor
public class GuestResource {
	
	private final GuestService guestService;
	
	@PostMapping
	private ResponseEntity<Object> cadastrarHospede(@RequestBody GuestDTO dto ) {
		try {
			Guest guest = Guest.builder()
					.name(dto.getName())
					.cpf(dto.getCpf())
					.phone(dto.getPhone())
					.status(Status.valueOf(dto.getStatus()))
					.build();
			
			guestService.salvar(guest);
			
			return new ResponseEntity<Object>(guest, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
