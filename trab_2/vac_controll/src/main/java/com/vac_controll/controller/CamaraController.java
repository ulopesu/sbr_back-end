package com.vac_controll.controller;

import java.util.List;
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

import com.vac_controll.model.Camara;
import com.vac_controll.model.Constante;
import com.vac_controll.model.Localizacao;
import com.vac_controll.repository.CamaraRepository;
import com.vac_controll.repository.LocalizacaoRepository;


@RestController
@RequestMapping("/camara")
public class CamaraController {
	
	@Autowired
	private CamaraRepository camRepository;
	
	@Autowired
	private LocalizacaoRepository locRepository;
	

	@GetMapping
	public Iterable<Camara> list() {
		return camRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus
	public Camara create(@RequestBody Camara cam) {
		if (cam.getLoc()!=null) {
			cam.setLoc(locRepository.save(cam.getLoc()));
		}

		return camRepository.save(cam);
	}
	
	@GetMapping(value="/{id}")
	public Optional<Camara> retrive(@PathVariable("id") long id) {
		return camRepository.findById(id);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Camara> update(@PathVariable("id") long id, @RequestBody Camara cam) {
		return camRepository.findById(id)
			.map(record -> {
				record.setNome(cam.getNome());
				if (cam.getLoc()!=null) {
					record.setLoc(locRepository.save(cam.getLoc()));
				}
				record.setTemperatura(cam.getTemperatura());
				record.setUmidade(cam.getUmidade());
				Camara updated = camRepository.save(record);
				return ResponseEntity.ok().body(updated);
			}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return camRepository.findById(id)
	           .map(record -> {
	        	   camRepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
}
