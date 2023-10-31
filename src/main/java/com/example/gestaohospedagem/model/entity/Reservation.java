package com.example.gestaohospedagem.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "prev_checkin")
	private LocalDateTime prevCheckin;
	
	@Column(name = "prev_checkout")
	private LocalDateTime prevCheckout;
	
	@Column(name = "checkin")
	private LocalDateTime  checkin;
	
	@Column(name = "checkout")
	private LocalDateTime  checkout;
	
	@ManyToOne(targetEntity = Guest.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_guest", referencedColumnName = "id")
	private Guest guest;
}
