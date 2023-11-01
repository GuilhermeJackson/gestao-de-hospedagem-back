package com.example.gestaohospedagem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.example.gestaohospedagem.model.entity.Guest;
import com.example.gestaohospedagem.repository.GuestRepository;
import com.example.gestaohospedagem.service.GuestService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GuestServiceImpl implements GuestService{
	
	private GuestRepository guestRepository;
	
	public GuestServiceImpl(GuestRepository repository) {
		super();
		this.guestRepository = repository;
	}

	@Override
	public Guest salvar(Guest guest) {
		validateName(guest.getName());
		validateCpf(guest.getCpf());
		validatePhone(guest.getPhone());
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
	
	@Override
	public void validateCpf(String cpf) {
		String validateCpf= cpf.trim();
		boolean isExist = guestRepository.existsByCpf(validateCpf);
		if(isExist) {
			throw new RuntimeException("Já existe um usuário cadastrado com esse CPF");
		}
		if(validateCpf.isEmpty()) {
			throw new RuntimeException("CPF está vazio");
		}
		if(validateCpf.length() != 11) {
			throw new RuntimeException(
					"CPF inválido. Preencha o campo com somente números e até 11 caracteres"
			);
		}
	    if (!validateCpf.matches("[0-9]+")) {
	        throw new RuntimeException("CPF deve conter apenas números.");
	    }
	}
	
	@Override
	public void validateName(String name) {
		String validateName = name.trim();
		boolean isExist = guestRepository.existsByName(validateName);
		if(isExist) {
			throw new RuntimeException("Já existe um usuário cadastrado com esse nome");
		}
		if(validateName.isEmpty()) {
			throw new RuntimeException("Nome está vazio");
		}
	}

	@Override
	public void validatePhone(String phone) {
		boolean isExist = guestRepository.existsByPhone(phone);
		if(isExist) {
			throw new RuntimeException("Telefone já cadastrado!");
		}
		if(phone.isEmpty()) {
			throw new RuntimeException("Telefone está vazio");
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Guest> find(Guest guestFilter) {
		Example<Guest> example = Example.of(guestFilter, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		return guestRepository.findAll(example);
	}	
}
