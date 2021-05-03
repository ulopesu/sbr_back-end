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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vac_controll.model.Camara;
import com.vac_controll.model.Constante;
import com.vac_controll.model.Lote;
import com.vac_controll.model.Vacina;

@RestController
@RequestMapping("/lote")
public class LoteController {
	static Long ids = Long.valueOf(1);
	static HashMap<Long, FactHandle> lotes_fact = new HashMap<Long, FactHandle>();
	
	@GetMapping
	public List<Lote> list() {
		List<Lote> lotes = new ArrayList<Lote>();

		QueryResults results = Constante.kSession.getQueryResults("listLote");
		for (QueryResultsRow row : results) {
			Lote lote = (Lote) row.get("lote");
			lotes.add(lote);
		}
		return lotes;
	}

	@CrossOrigin("*")
	@GetMapping(value="/camara/{camara_id}")
	public List<Lote> list(@PathVariable("camara_id") long id) {
		List<Lote> lotes = new ArrayList<Lote>();
		QueryResults results = Constante.kSession.getQueryResults("filterLotesByCamaraId", id);
		for (QueryResultsRow row : results) {
			Lote lote = (Lote) row.get("lote");
			lotes.add(lote);
		}
		return lotes;
	}

	@PostMapping
	@ResponseStatus
	public ResponseEntity<Lote> create(@RequestBody Lote lote) {
		
		lote.setId(ids);
		ids++;

		lote.setUtil(true);

		// SET VACINA BY ID
		if (lote.getVacina() != null &&
			lote.getVacina().getId() != null
		) {
			Long vac_id = lote.getVacina().getId();
			QueryResults results = Constante.kSession.getQueryResults("getVacinaById", vac_id);
			for (QueryResultsRow row : results) {
				Vacina vacina = (Vacina) row.get("vacina");
				lote.setVacina(vacina);
			}
		}

		// SET CAMARA BY ID
		if (lote.getCamara() != null &&
			lote.getCamara().getId() != null
		) {
			Long cam_id = lote.getCamara().getId();
			QueryResults results = Constante.kSession.getQueryResults("getCamaraById", cam_id);
			for (QueryResultsRow row : results) {
				Camara camara = (Camara) row.get("camara");
				lote.setCamara(camara);
			}
		}

		lotes_fact.put(lote.getId(), Constante.kSession.insert(lote));

		return ResponseEntity.created(URI.create("/lote/" + lote.getId())).body(lote);
	}
}
