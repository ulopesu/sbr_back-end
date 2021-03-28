package com.vac_controll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vac_controll.model.Lote;

@Repository
public interface LoteRepository extends CrudRepository<Lote, Long> {

}
