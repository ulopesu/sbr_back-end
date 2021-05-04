package com.vac_controll.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.vac_controll.model.LeituraSensorTemp;

@RestController
@RequestMapping("/camara")
public class CamaraController {
	static Long camara_ids = Long.valueOf(1);
	static HashMap<Long, FactHandle> camara_facts = new HashMap<Long, FactHandle>();

	static Long leitura_ids = Long.valueOf(1);
	static HashMap<Long, FactHandle> leituras_facts = new HashMap<Long, FactHandle>();

	@PostMapping
	@ResponseStatus
	public ResponseEntity<Camara> create(@RequestBody Camara cam) {

		cam.setCodigo(CodigoAlerta.TEMP_OK);
		cam.setAlertaDefeito(false);

		cam.setId(camara_ids);
		camara_ids++;

		cam.getLoc().setId(Constante.localizacao_ids);
		Constante.localizacao_ids++;
		camara_facts.put(cam.getId(), Constante.kSession.insert(cam));

		LeituraSensorTemp new_leitura = new LeituraSensorTemp(cam, cam.getTemperatura());
		new_leitura.setId(leitura_ids);
		new_leitura.setAtual(false);
		leitura_ids++;
		leituras_facts.put(new_leitura.getId(), Constante.kSession.insert(new_leitura));
		return ResponseEntity.created(URI.create("/camara/" + cam.getId())).body(cam);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Camara> update(@PathVariable("id") long id, @RequestBody Camara cam) {
		FactHandle fact = camara_facts.get(id);
		if (fact != null) {
			Camara camara = (Camara) Constante.kSession.getObject(fact);

			LeituraSensorTemp new_leitura = new LeituraSensorTemp(camara, cam.getTemperatura());

			new_leitura.setId(leitura_ids);
			leitura_ids++;
			leituras_facts.put(new_leitura.getId(), Constante.kSession.insert(new_leitura));

			return ResponseEntity.ok().body(camara);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin("*")
	@GetMapping
	public List<Camara> list() {
		List<Camara> camaras = new ArrayList<Camara>();

		QueryResults results = Constante.kSession.getQueryResults("listCamara");
		for (QueryResultsRow row : results) {
			Camara camara = (Camara) row.get("camara");
			camaras.add(camara);
		}
		return camaras;
	}

	@CrossOrigin("*")
	@GetMapping(value = "/{id}/historico")
	public List<LeituraSensorTemp> listHistorico(@PathVariable("id") long id) {
		List<LeituraSensorTemp> leituras = new ArrayList<LeituraSensorTemp>();

		QueryResults results = Constante.kSession.getQueryResults("filterLeituraByCamaraId", id);
		for (QueryResultsRow row : results) {
			LeituraSensorTemp leitura = (LeituraSensorTemp) row.get("leitura");
			leituras.add(leitura);
		}
		return leituras;
	}
}
