package com.vac_controll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.vac_controll.model.Vacina;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {
		
}
