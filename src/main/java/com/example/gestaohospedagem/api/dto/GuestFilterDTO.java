package com.example.gestaohospedagem.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestFilterDTO {
	private String name;
	private String cpf;
	private String phone;
}
