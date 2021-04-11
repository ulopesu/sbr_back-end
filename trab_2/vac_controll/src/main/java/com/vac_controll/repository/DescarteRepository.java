package com.vac_controll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vac_controll.model.Descarte;

@Repository
public interface DescarteRepository extends JpaRepository<Descarte, Long> {

}
