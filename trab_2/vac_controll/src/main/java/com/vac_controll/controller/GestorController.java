package com.vac_controll.controller;

import java.net.URI;
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
import com.vac_controll.model.Gestor;
import com.vac_controll.model.Lote;
import com.vac_controll.model.Vacina;
import com.vac_controll.repository.CamaraRepository;
import com.vac_controll.repository.GestorRepository;
import com.vac_controll.repository.LocalizacaoRepository;

@RestController
@RequestMapping("/gestor")
public class GestorController {

	@Autowired
	private GestorRepository gestorRepository;

	@Autowired
	private LocalizacaoRepository locRepository;

	@Autowired
	private CamaraRepository camRepository;

	@GetMapping
	public Iterable<Gestor> list() {
		return gestorRepository.findAll();
	}

	@GetMapping(value = "/{id}")
	public Optional<Gestor> retrive(@PathVariable("id") long id) {
		return gestorRepository.findById(id);
	}

	@PostMapping
	@ResponseStatus
	public ResponseEntity<Gestor> create(@RequestBody Gestor gestor) {
		if (gestor.getLoc() != null) {
			gestor.setLoc(locRepository.save(gestor.getLoc()));
		}

		// SET CAMARA BY ID
		if (gestor.getCamara() != null) {
			Long cam_id = gestor.getCamara().getId();
			Optional<Camara> cam = camRepository.findById(cam_id);
			cam.ifPresent((my_cam) -> {
				gestor.setCamara(my_cam);
			});
		}

		Gestor new_gestor = gestorRepository.save(gestor);

		return ResponseEntity.created(URI.create("/gestor/" + new_gestor.getId())).body(new_gestor);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Gestor> update(@PathVariable("id") long id, @RequestBody Gestor gestor) {
		return gestorRepository.findById(id).map(record -> {
			record.setNome(gestor.getNome());
			record.setEmail(gestor.getEmail());
			record.setTelefone(gestor.getTelefone());

			if (gestor.getLoc() != null) {
				record.setLoc(locRepository.save(gestor.getLoc()));
			}

			// SET CAMARA BY ID
			if (gestor.getCamara() != null) {
				Long cam_id = gestor.getCamara().getId();
				Optional<Camara> cam = camRepository.findById(cam_id);
				cam.ifPresent((my_cam) -> {
					record.setCamara(my_cam);
				});
			}
			Gestor updated = gestorRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable long id) {
		return gestorRepository.findById(id).map(record -> {
			gestorRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
