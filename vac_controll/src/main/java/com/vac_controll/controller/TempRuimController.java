package com.vac_controll.controller;

import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import com.vac_controll.model.Constante;
import com.vac_controll.model.TempRuim;

@RestController
@RequestMapping("/temp_ruim")
public class TempRuimController {

	@GetMapping
	public List<TempRuim> list() {
		List<TempRuim> temps = new ArrayList<TempRuim>();
		QueryResults results = Constante.kSession.getQueryResults("listTempRuim");
		for (QueryResultsRow row : results) {
			TempRuim tempRuim = (TempRuim) row.get("tempRuim");
			temps.add(tempRuim);
		}
		return temps;
	}
}
