package com.vac_controll.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.vac_controll.model.HistoricoCamara;
import com.vac_controll.repository.CamaraRepository;
import com.vac_controll.repository.HistoricoCamaraRepository;
import com.vac_controll.repository.LocalizacaoRepository;

@RestController
@RequestMapping("/camara")
public class CamaraController {

	@Autowired
	private CamaraRepository camRepository;

	@Autowired
	private LocalizacaoRepository locRepository;

	@Autowired
	private HistoricoCamaraRepository historicoCamRepository;

	@CrossOrigin("*")
	@GetMapping
	public Iterable<Camara> list() {
		return camRepository.findAll();
	}

	@PostMapping
	@ResponseStatus
	public Camara create(@RequestBody Camara cam) {
		if (cam.getLoc() != null) {
			cam.setLoc(locRepository.save(cam.getLoc()));
		}

		cam.setTempcor(Constante.TEMP_COR_OK);

		Camara new_cam = camRepository.save(cam);

		double temp = new_cam.getTemperatura();
		HistoricoCamara new_historico = new HistoricoCamara(new Date(), new_cam, temp);
		historicoCamRepository.save(new_historico);

		return new_cam;
	}

	@GetMapping(value = "/{id}")
	public Optional<Camara> retrive(@PathVariable("id") long id) {
		return camRepository.findById(id);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Camara> update(@PathVariable("id") long id, @RequestBody Camara cam) {
		return camRepository.findById(id).map(record -> {
			if (cam.getNome() != null) {
				record.setNome(cam.getNome());
			}

			if (cam.getLoc() != null) {
				record.setLoc(locRepository.save(cam.getLoc()));
			}

			double temp = cam.getTemperatura();

			record.setTemperatura(temp);

			Camara updated = camRepository.save(record);

			HistoricoCamara new_historico = new HistoricoCamara(new Date(), updated, temp);
			historicoCamRepository.save(new_historico);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable long id) {
		return camRepository.findById(id).map(record -> {
			camRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
