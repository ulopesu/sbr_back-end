package com.vac_controll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vac_controll.model.TempRuim;

@Repository
public interface TempRuimRepository extends JpaRepository<TempRuim, Long> {
}
