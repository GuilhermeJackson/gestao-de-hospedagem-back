package com.example.gestaohospedagem.service.impl;

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

}
