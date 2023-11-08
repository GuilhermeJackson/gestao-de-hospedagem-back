package com.example.gestaohospedagem.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "value_weekday")
	private int valueWeekday;
	
	@Column(name = "value_weekendays")
	private int valueWeekendays;
	
	@Column(name = "qt_weekday")
	private int qtWeekday;
	
	@Column(name = "qt_weekendays")
	private int qtWeekendays;
	
	@Column(name = "total_value")
	private BigDecimal totalValue;
	
	@Column(name = "value_garage_weekday")
	private int valueGarageWeekday;
	
	@Column(name = "value_garage_weekendays")
	private int valueGarageWeekendays;
	
    @OneToOne(targetEntity = Reserve.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reserve", referencedColumnName = "id")
    private Reserve reserve;
}
