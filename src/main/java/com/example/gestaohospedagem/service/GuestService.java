package com.example.gestaohospedagem.service;

import java.util.List;
import java.util.Optional;

import com.example.gestaohospedagem.model.entity.Guest;

public interface GuestService {
	Guest salvar(Guest guest);
	
	List<Guest> getListGuest();
	
	Optional<Guest> findById(Long id);

	void validateName(String name);
	
	void validateCpf(String cpf);
	
	void validatePhone(String phone);
	
	List<Guest> find(Guest guest);
}
