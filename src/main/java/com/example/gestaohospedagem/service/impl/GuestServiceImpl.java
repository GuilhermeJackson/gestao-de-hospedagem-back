package com.example.gestaohospedagem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestaohospedagem.model.entity.Guest;
import com.example.gestaohospedagem.repository.GuestRepository;
import com.example.gestaohospedagem.service.GuestService;

@Service
public class GuestServiceImpl implements GuestService{
	
	@Autowired
	private GuestRepository guestRepository;

	@Override
	public Guest salvar(Guest guest) {
		if(guest.getName() == null) {
			throw new RuntimeException("Nome do hóspede vazio!");
		}
		return guestRepository.save(guest);
	}

	@Override
	public Optional<Guest> findById(Long id) {
		return guestRepository.findById(id);
	}

	@Override
	public List<Guest> getListGuest() {
		List<Guest> guests = guestRepository.findAll();
		if (guests.isEmpty()) {
			throw new RuntimeException("Lista de hóspedes vazia!");
		}
		return guests;
	}

}
