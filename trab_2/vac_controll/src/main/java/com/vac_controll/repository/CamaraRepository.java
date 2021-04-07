package com.vac_controll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vac_controll.model.Camara;

@Repository
public interface CamaraRepository extends JpaRepository <Camara, Long> {

}