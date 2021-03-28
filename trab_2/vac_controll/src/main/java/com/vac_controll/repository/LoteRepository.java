package com.vac_controll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vac_controll.model.Lote;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {
}
