//package com.example.gestaohospedagem.model.entity;
//
//import java.time.LocalDate;
//
//import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Convert;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToMany;
//
//@Entity
//public class Reserva {
//	
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	@Column(name = "checkin_data")
//	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
//	private LocalDate checkin;
//	
//	@Column(name = "checkout_data")
//	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
//	private LocalDate checkout;
//	
//	@Column(name = "isVagaCaragem")
//	private boolean isVagaCaragem;
//	
//	@JoinColumn(name = "id_usuario")
//	@OneToMany
//	private Guest reserva;
//}
