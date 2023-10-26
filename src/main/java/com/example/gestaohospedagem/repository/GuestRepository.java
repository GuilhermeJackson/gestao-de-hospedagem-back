package com.example.gestaohospedagem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestaohospedagem.model.entity.Guest;


public interface GuestRepository extends JpaRepository<Guest, Long>{

}
