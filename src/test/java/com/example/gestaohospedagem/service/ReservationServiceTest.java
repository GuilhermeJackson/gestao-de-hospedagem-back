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

import com.example.gestaohospedagem.model.entity.Reservation;
import com.example.gestaohospedagem.repository.GuestRepository;
import com.example.gestaohospedagem.repository.ReservationRepository;
import com.example.gestaohospedagem.service.impl.GuestServiceImpl;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class ReservationServiceTest {
	@SpyBean
	ReservationService service;
	
	@MockBean
	ReservationRepository repository;
	
	@Test
    public void shouldSaveReservation() {
        Reservation reservation = createSampleReservation();
        Mockito.when(repository.save(Mockito.any(Reservation.class))).thenReturn(reservation);

        Reservation savedReservation = service.salvar(reservation);

        Assertions.assertThat(savedReservation).isNotNull();
        Assertions.assertThat(savedReservation.getId()).isEqualTo(1L);
        Assertions.assertThat(savedReservation.getPrevCheckin()).isEqualTo(reservation.getPrevCheckin());
        Assertions.assertThat(savedReservation.getPrevCheckout()).isEqualTo(reservation.getPrevCheckout());
        Assertions.assertThat(savedReservation.getCheckin()).isEqualTo(reservation.getCheckin());
        Assertions.assertThat(savedReservation.getCheckout()).isEqualTo(reservation.getCheckout());
    }

    @Test
    public void shouldFindReservationById() {
        Reservation reservation = createSampleReservation();
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(reservation));

        Optional<Reservation> foundReservation = service.findById(1L);

        Assertions.assertThat(foundReservation).isPresent();
        Assertions.assertThat(foundReservation.get()).isEqualTo(reservation);
    }

    @Test
    public void shouldFindAllReservationsWithGuest() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(createSampleReservation());
        reservations.add(createSampleReservation());

        Mockito.when(repository.findAllReservationsWithGuest()).thenReturn(reservations);

        List<Reservation> foundReservations = service.findAllReservationsWithGuest();

        Assertions.assertThat(foundReservations).isNotNull();
        Assertions.assertThat(foundReservations).hasSize(2);
        Assertions.assertThat(foundReservations).containsAll(reservations);
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
        Reservation reservation = Reservation.builder()
                .id(1L)
                .prevCheckin(LocalDateTime.now())
                .prevCheckout(LocalDateTime.now().minusDays(1))
                .build();
        Mockito.when(repository.save(Mockito.any(Reservation.class))).thenReturn(reservation);
        service.salvar(reservation);
    }

    private Reservation createSampleReservation() {
        return Reservation.builder()
                .id(1L)
                .prevCheckin(LocalDateTime.now())
                .prevCheckout(LocalDateTime.now().plusDays(1))
                .build();
    }
}
