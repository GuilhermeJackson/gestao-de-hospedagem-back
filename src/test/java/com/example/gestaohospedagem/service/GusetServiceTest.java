package com.example.gestaohospedagem.service;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.gestaohospedagem.model.entity.Guest;
import com.example.gestaohospedagem.repository.GuestRepository;
import com.example.gestaohospedagem.service.impl.GuestServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class GusetServiceTest {
	@SpyBean
	GuestServiceImpl service;
	
	@MockBean
	GuestRepository repository;
	
	@Test(expected = Test.None.class)
	public void shouldSaveUser() {
		 Mockito.doNothing().when(service).validateName(Mockito.anyString());
		 Guest guest = createNewGuest();
		 
		 Mockito.when(repository.save(Mockito.any(Guest.class))).thenReturn(guest);
		 
		 Guest guestSalvo = service.salvar(guest);
		 
		 Assertions.assertThat(guestSalvo).isNotNull();
		 Assertions.assertThat(guestSalvo.getId()).isEqualTo(1l);
		 Assertions.assertThat(guestSalvo.getName()).isEqualTo("Pedro Pedroso");
		 Assertions.assertThat(guestSalvo.getCpf()).isEqualTo("00000000021");
		 Assertions.assertThat(guestSalvo.getPhone()).isEqualTo("47 999999999");
	}
	
	@Test(expected = RuntimeException.class)
	public void notShouldSaveUserWithAnAlreadyRegisteredEmailAddress() {
		String name = "Pedro Pedroso";
		Guest guest = Guest.builder().name(name).build();
		Mockito.doThrow(RuntimeException.class).when(service).validateName(name);
		
		service.salvar(guest);
		
		Mockito.verify(repository, Mockito.never()).save(guest);
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldNotSaveUserWithEmptyName() {
	    Guest guest = Guest.builder().name("").cpf("12345678901").phone("1234567890").build();
	    service.salvar(guest);

	    Mockito.verify(repository, Mockito.never()).save(guest);
	}

	@Test(expected = RuntimeException.class)
	public void shouldNotSaveUserWithInvalidCPF() {
	    Guest guest = Guest.builder().name("John Doe").cpf("1234567890A").phone("1234567890").build();
	    service.salvar(guest);

	    Mockito.verify(repository, Mockito.never()).save(guest);
	}

	@Test(expected = RuntimeException.class)
	public void shouldNotSaveUserWithEmptyPhone() {
	    Guest guest = Guest.builder().name("John Doe").cpf("12345678901").phone("").build();
	    service.salvar(guest);

	    Mockito.verify(repository, Mockito.never()).save(guest);
	}

	@Test(expected = RuntimeException.class)
	public void shouldNotSaveUserWithInvalidCPFLength() {
	    Guest guest = Guest.builder().name("John Doe").cpf("132345678901").phone("1234567890").build();
	    service.salvar(guest);

	    Mockito.verify(repository, Mockito.never()).save(guest);
	}

	@Test(expected = RuntimeException.class)
	public void shouldNotSaveUserWithExistingCPF() {
	    String existingCPF = "00000000021";
	    Guest existingGuest = createNewGuest();

	    Mockito.when(repository.existsByCpf(existingCPF)).thenReturn(true);

	    Guest newGuest = Guest.builder().name("John Doe").cpf(existingCPF).phone("1234567890").build();
	    service.salvar(newGuest);

	    Mockito.verify(repository, Mockito.never()).save(newGuest);
	}
	
	private Guest createNewGuest() {
	    Guest guest = Guest.builder()
	            .id(1l)
	            .name("Pedro Pedroso")
	            .cpf("00000000021")
	            .phone("47 999999999")
	            .build();
	    return guest;
	}


}
