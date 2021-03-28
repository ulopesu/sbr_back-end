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

import com.vac_controll.model.Lote;
import com.vac_controll.repository.LoteRepository;

@RestController
@RequestMapping("/lote")
public class LoteController {
	
	@Autowired
	private LoteRepository repository;
	
	@GetMapping
	public Iterable<Lote> list() {
		return repository.findAll();
	}
	
	@PostMapping
	@ResponseStatus
	public Lote create(@RequestBody Lote lote) {
		return repository.save(lote);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Lote> update(@PathVariable("id") long id, @RequestBody Lote lote) {
		return repository.findById(id)
			.map(record -> {
				record.setQtd(lote.getQtd());
				record.setCam(lote.getCam());
				record.setVac(lote.getVac());
				record.setValidade(lote.getValidade());
				record.setUtil(lote.isUtil());
				Lote updated = repository.save(record);
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
