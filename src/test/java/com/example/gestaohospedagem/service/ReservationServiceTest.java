package com.example.gestaohospedagem.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.gestaohospedagem.model.entity.Reserve;
import com.example.gestaohospedagem.repository.ReserveRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class ReservationServiceTest {
	@SpyBean
	ReserveService service;
	
	@MockBean
	ReserveRepository repository;
	
	@Test
    public void shouldSaveReservation() {
        Reserve reserve = createSampleReservation();
        Mockito.when(repository.save(Mockito.any(Reserve.class))).thenReturn(reserve);

        Reserve savedReservation = service.salvar(reserve);

        Assertions.assertThat(savedReservation).isNotNull();
        Assertions.assertThat(savedReservation.getId()).isEqualTo(1L);
        Assertions.assertThat(savedReservation.getPrevCheckin()).isEqualTo(reserve.getPrevCheckin());
        Assertions.assertThat(savedReservation.getPrevCheckout()).isEqualTo(reserve.getPrevCheckout());
        Assertions.assertThat(savedReservation.getCheckin()).isEqualTo(reserve.getCheckin());
        Assertions.assertThat(savedReservation.getCheckout()).isEqualTo(reserve.getCheckout());
    }

    @Test
    public void shouldFindReservationById() {
        Reserve reserve = createSampleReservation();
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(reserve));

        Optional<Reserve> foundReservation = service.findById(1L);

        Assertions.assertThat(foundReservation).isPresent();
        Assertions.assertThat(foundReservation.get()).isEqualTo(reserve);
    }

    @Test
    public void shouldFindAllReservationsWithGuest() {
        List<Reserve> reserves = new ArrayList<>();
        reserves.add(createSampleReservation());
        reserves.add(createSampleReservation());

        Mockito.when(repository.findAllReservationsWithGuest()).thenReturn(reserves);

        List<Reserve> foundReservations = service.findAllReservationsWithGuest();

        Assertions.assertThat(foundReservations).isNotNull();
        Assertions.assertThat(foundReservations).hasSize(2);
        Assertions.assertThat(foundReservations).containsAll(reserves);
    }
    
    @Test(expected = RuntimeException.class)
    public void shouldFailToSaveNullReservation() {
        service.salvar(null);
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToFindNonExistentReservation() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());
        service.findById(1L);
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToFindAllReservationsWithGuestWhenEmpty() {
        Mockito.when(repository.findAllReservationsWithGuest()).thenReturn(null);
        service.findAllReservationsWithGuest();
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToFindAllReservationsWithGuestWhenNoneFound() {
        Mockito.when(repository.findAllReservationsWithGuest()).thenReturn(new ArrayList<>());
        service.findAllReservationsWithGuest();
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailToSaveReservationWithInvalidCheckinCheckout() {
        Reserve reserve = Reserve.builder()
                .id(1L)
                .prevCheckin(LocalDateTime.now())
                .prevCheckout(LocalDateTime.now().minusDays(1))
                .build();
        Mockito.when(repository.save(Mockito.any(Reserve.class))).thenReturn(reserve);
        service.salvar(reserve);
    }

    private Reserve createSampleReservation() {
        return Reserve.builder()
                .id(1L)
                .prevCheckin(LocalDateTime.now())
                .prevCheckout(LocalDateTime.now().plusDays(1))
                .build();
    }
}
