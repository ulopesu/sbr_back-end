package com.vac_controll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vac_controll.model.Gestor;

@Repository
public interface GestorRepository extends CrudRepository<Gestor, Long> {

}