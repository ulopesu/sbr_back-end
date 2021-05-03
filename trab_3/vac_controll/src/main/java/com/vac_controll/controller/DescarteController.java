package com.vac_controll.controller;

import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import com.vac_controll.model.Constante;
import com.vac_controll.model.Descarte;

@RestController
@RequestMapping("/descarte")
public class DescarteController {
	
	@GetMapping
	public List<Descarte> list() { 
		List<Descarte> descartes = new ArrayList<Descarte>();

		QueryResults results = Constante.kSession.getQueryResults("listDescarte");
		for (QueryResultsRow row : results) {
			Descarte descarte = (Descarte) row.get("descarte");
			descartes.add(descarte);
		}
		return descartes;
	}
}
