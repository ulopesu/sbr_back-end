package com.vac_controll.controller;

import java.net.URI;
import java.util.HashMap;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vac_controll.model.Constante;
import com.vac_controll.model.Vacina;


@RestController
@RequestMapping("/vacina")
public class VacinaController {
	static Long ids = Long.valueOf(1);
	static HashMap<Long, FactHandle> vacinas_fact = new HashMap<Long, FactHandle>();
	
	@PostMapping
	@ResponseStatus
	public ResponseEntity<Vacina> create(@RequestBody Vacina vac) {
		vac.setId(ids);
		ids++;
		vacinas_fact.put(vac.getId(), Constante.kSession.insert(vac));
		return ResponseEntity.created(URI.create("/vacina/" + vac.getId())).body(vac);
	}
}
