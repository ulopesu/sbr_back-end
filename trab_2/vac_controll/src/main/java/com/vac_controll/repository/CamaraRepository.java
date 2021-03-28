package com.vac_controll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vac_controll.model.Camara;

@Repository
public interface CamaraRepository extends CrudRepository <Camara, Long> {

}