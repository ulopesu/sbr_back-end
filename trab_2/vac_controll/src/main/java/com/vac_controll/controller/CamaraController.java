package com.vac_controll.controller;

import java.net.URI;
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
import com.vac_controll.model.CodigoAlerta;
import com.vac_controll.model.HistoricoCamara;
import com.vac_controll.model.Lote;
import com.vac_controll.repository.CamaraRepository;
import com.vac_controll.repository.HistoricoCamaraRepository;
import com.vac_controll.repository.LocalizacaoRepository;
import com.vac_controll.repository.LoteRepository;

@RestController
@RequestMapping("/camara")
public class CamaraController {

	@Autowired
	private CamaraRepository camRepository;

	@Autowired
	private LoteRepository loteRepository;

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
	public ResponseEntity<Camara> create(@RequestBody Camara cam) {
		if (cam.getLoc() != null) {
			cam.setLoc(locRepository.save(cam.getLoc()));
		}

		cam.setCodigo(CodigoAlerta.TEMP_OK);

		cam.setAlertaDefeito(false);

		cam.setFoiAlterada(false);

		Camara new_cam = camRepository.save(cam);

		double temp = new_cam.getTemperatura();
		HistoricoCamara new_historico = new HistoricoCamara(new Date(), new_cam, temp);
		historicoCamRepository.save(new_historico);
		
		return ResponseEntity.created(URI.create("/camara/" + new_cam.getId())).body(new_cam);
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
			record.setFoiAlterada(true);

			// ATUALIZA CODIGO DA CAMARA
			Iterable<Lote> lotes = loteRepository.findByCamaraIdAndUtilTrue(id);
			// System.out.println(lotes);
			boolean temp_ruim = false;
			for (Lote lote : lotes) {
				CodigoAlerta codigo = lote.checarTemp();
				if (codigo == CodigoAlerta.MARGEM_MIN || codigo == CodigoAlerta.MARGEM_MAX) {
					temp_ruim = true;
					record.setCodigo(CodigoAlerta.MARGEM_MAX);
				} else if (codigo == CodigoAlerta.TEMP_MIN || codigo == CodigoAlerta.TEMP_MAX) {
					temp_ruim = true;
					record.setCodigo(CodigoAlerta.TEMP_MAX);
					break;
				}
			}
			if (!temp_ruim) {
				record.setCodigo(CodigoAlerta.TEMP_OK);
			}

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
