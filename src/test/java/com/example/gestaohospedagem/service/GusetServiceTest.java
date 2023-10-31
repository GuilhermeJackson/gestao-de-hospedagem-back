package com.example.gestaohospedagem.service;

import org.junit.Test;
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
		 Assertions.assertThat(guestSalvo.getCpf()).isEqualTo("000.000.000.21");
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
	
	private Guest createNewGuest() {
	    Guest guest = Guest.builder()
	            .id(1l)
	            .name("Pedro Pedroso")
	            .cpf("000.000.000.21")
	            .phone("47 999999999")
	            .build();
	    return guest;
	}


}
