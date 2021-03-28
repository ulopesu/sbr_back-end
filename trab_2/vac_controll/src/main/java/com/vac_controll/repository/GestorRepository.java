package com.vac_controll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vac_controll.model.Gestor;

@Repository
public interface GestorRepository extends JpaRepository<Gestor, Long> {
	
	List<Gestor> findByCamaraId(Long id);
}