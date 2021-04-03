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
import com.vac_controll.model.CodigoAlerta;
import com.vac_controll.model.Constante;
import com.vac_controll.model.Gestor;
import com.vac_controll.model.Lote;
import com.vac_controll.model.Vacina;
import com.vac_controll.repository.CamaraRepository;
import com.vac_controll.repository.GestorRepository;
import com.vac_controll.repository.LoteRepository;

import com.vac_controll.repository.VacinaRepository;

@RestController
@RequestMapping("/lote")
public class LoteController {

	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	private VacinaRepository vacRepository;

	@Autowired
	private CamaraRepository camRepository;

	@Autowired
	private GestorRepository gestorRepository;

	@GetMapping
	public Iterable<Lote> list() {
		return loteRepository.findAll();
	}

	@PostMapping
	@ResponseStatus
	public Lote create(@RequestBody Lote lote) {
		// SET VACINA BY ID

		if (lote.getVacina() != null) {
			Long vac_id = lote.getVacina().getId();
			Optional<Vacina> vac = vacRepository.findById(vac_id);
			vac.ifPresent((my_vac) -> {
				lote.setVacina(my_vac);
			});
		}

		// SET CAMARA BY ID
		if (lote.getCamara() != null) {
			Long cam_id = lote.getCamara().getId();
			Optional<Camara> cam = camRepository.findById(cam_id);
			cam.ifPresent((my_cam) -> {
				lote.setCamara(my_cam);
			});
		}

		if (loteRepository.count() == 0) {
			Thread t1 = new Thread(
				new FireRules(
						vacRepository, 
						camRepository, 
						gestorRepository, 
						loteRepository
						)
				);
			t1.start();
		}
		return loteRepository.save(lote);
	}

	@GetMapping(value = "/{id}")
	public Optional<Lote> retrive(@PathVariable("id") long id) {
		return loteRepository.findById(id);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Lote> update(@PathVariable("id") long id, @RequestBody Lote lote) {
		return loteRepository.findById(id).map(record -> {
			record.setQtd(lote.getQtd());
			record.setValidade(lote.getValidade());
			record.setUtil(lote.isUtil());

			// SET VACINA BY ID
			Long vac_id = lote.getVacina().getId();
			Optional<Vacina> vac = vacRepository.findById(vac_id);
			vac.ifPresent((my_vac) -> {
				lote.setVacina(my_vac);
			});

			// SET CAMARA BY ID
			Long cam_id = lote.getCamara().getId();
			Optional<Camara> cam = camRepository.findById(cam_id);
			cam.ifPresent((my_cam) -> {
				lote.setCamara(my_cam);
			});

			Lote updated = loteRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable long id) {
		return loteRepository.findById(id).map(record -> {
			loteRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	public void notificarGestores(CodigoAlerta cod, Lote lote) {
		List<Gestor> gestores = gestorRepository.findByCamaraId(lote.getCamara().getId());
		for (Gestor gestor : gestores) {
			gestor.enviarMsg(lote, cod);
		}
	}

	public void chamarGestor(CodigoAlerta cod, Lote lote) {
		List<Gestor> gestores = gestorRepository.findByCamaraId(lote.getCamara().getId());
		Gestor gMaisProx = lote.getCamara().getLoc().gestorMaisProx(gestores);
		// System.out.println(lote.getVac().getNome());
		gMaisProx.enviarMsg(lote, cod);
	}
}
