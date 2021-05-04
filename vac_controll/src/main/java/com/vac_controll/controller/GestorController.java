package com.vac_controll.controller;

import java.net.URI;
import java.util.HashMap;

import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vac_controll.model.Camara;
import com.vac_controll.model.Constante;
import com.vac_controll.model.Gestor;

@RestController
@RequestMapping("/gestor")
public class GestorController {
	static Long ids = Long.valueOf(1);
	static HashMap<Long, FactHandle> gestores_fact = new HashMap<Long, FactHandle>();

	@PostMapping
	@ResponseStatus
	public ResponseEntity<Gestor> create(@RequestBody Gestor gestor) {
		gestor.setId(ids);
		ids++;

		gestor.getLoc().setId(Constante.localizacao_ids);
		Constante.localizacao_ids++;

		// SET CAMARA BY ID
		if (gestor.getCamara() != null) {
			Long cam_id = gestor.getCamara().getId();
			QueryResults results = Constante.kSession.getQueryResults("getCamaraById", cam_id);
			for (QueryResultsRow row : results) {
				Camara camara = (Camara) row.get("camara");
				gestor.setCamara(camara);
			}
		}

		gestores_fact.put(gestor.getId(), Constante.kSession.insert(gestor));
		return ResponseEntity.created(URI.create("/gestor/" + gestor.getId())).body(gestor);
	}
}
