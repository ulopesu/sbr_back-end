package com.vac_controll.controller;

import java.net.URI;
import java.util.Optional;

import org.kie.api.runtime.KieSession;
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

import com.vac_controll.model.Constante;
import com.vac_controll.model.Vacina;
import com.vac_controll.repository.VacinaRepository;

@RestController
@RequestMapping("/vacina")
public class VacinaController {
	
	@Autowired
	private VacinaRepository vacRepository;
		
	@GetMapping
	public Iterable<Vacina> list() {
		return vacRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus
	public ResponseEntity<Vacina> create(@RequestBody Vacina vac) {
		Vacina new_vac = vacRepository.save(vac);
		return ResponseEntity.created(URI.create("/vacina/" + new_vac.getId())).body(new_vac);
	}
	
	@GetMapping(value="/{id}")
	public Optional<Vacina> retrive(@PathVariable("id") long id) {
		return vacRepository.findById(id);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Vacina> update(@PathVariable("id") long id, @RequestBody Vacina vac) {
		return vacRepository.findById(id)
			.map(record -> {
				record.setNome(vac.getNome());
				record.setTempMax(vac.getTempMax());
				record.setTempMin(vac.getTempMin());
				record.setMargem(vac.getMargem());
				Vacina updated = vacRepository.save(record);
				return ResponseEntity.ok().body(updated);
			}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return vacRepository.findById(id)
	           .map(record -> {
	        	   vacRepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
}
