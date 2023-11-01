package com.example.gestaohospedagem.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReservationDTO {
	    private LocalDateTime prevCheckin;
	    private LocalDateTime prevCheckout;
	    private Long id_guest;
	    private boolean isGarage;
}
