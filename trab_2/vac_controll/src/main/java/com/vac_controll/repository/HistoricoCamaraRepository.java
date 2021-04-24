package com.vac_controll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.vac_controll.model.HistoricoCamara;

@Repository
public interface HistoricoCamaraRepository extends JpaRepository <HistoricoCamara, Long> {

    List<HistoricoCamara> findByCamaraId(Long id);

}