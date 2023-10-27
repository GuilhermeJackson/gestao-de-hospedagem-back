package com.example.gestaohospedagem.model.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Reservation {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "checkin")
	private LocalDateTime  checkin;
	
	@Column(name = "checkout")
	private LocalDateTime  checkout;
	
	@Column(name = "isGarage")
	private boolean isGarage;
	
	@ManyToOne
	@JoinColumn(name = "id_guest")
	private Guest id_guest;
}

