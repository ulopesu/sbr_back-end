package com.vac_controll.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vac_controll.model.Vacina;

@Repository
public interface VacinaRepository extends CrudRepository<Vacina, Long> {
		
}
