package com.vac_controll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vac_controll.model.Vacina;
import com.vac_controll.repository.VacinaRepository;

@RestController
@RequestMapping("/vacina")
public class VacinaController {
	
	@Autowired
	private VacinaRepository repository;
		
	@GetMapping
	public Iterable<Vacina> list() {
		return repository.findAll();
	}
	
	@PostMapping
	@ResponseStatus
	public Vacina create(@RequestBody Vacina vac) {
		return repository.save(vac);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Vacina> update(@PathVariable("id") long id, @RequestBody Vacina vac) {
		return repository.findById(id)
			.map(record -> {
				record.setNome(vac.getNome());
				record.setTempMax(vac.getTempMax());
				record.setTempMin(vac.getTempMin());
				record.setMargem(vac.getMargem());
				Vacina updated = repository.save(record);
				return ResponseEntity.ok().body(updated);
			}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return repository.findById(id)
	           .map(record -> {
	               repository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
}
