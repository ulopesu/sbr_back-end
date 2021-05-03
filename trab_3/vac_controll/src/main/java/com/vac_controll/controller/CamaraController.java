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
import com.vac_controll.model.Lote;
import com.vac_controll.model.TempRuim;

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

		LeituraSensorTemp new_leitura = new LeituraSensorTemp(cam, cam.getTemperatura());
		new_leitura.setId(leitura_ids);
		leitura_ids++;
		leituras_facts.put(new_leitura.getId(), Constante.kSession.insert(new_leitura));

		camara_facts.put(cam.getId(), Constante.kSession.insert(cam));
		return ResponseEntity.created(URI.create("/camara/" + cam.getId())).body(cam);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Camara> update(@PathVariable("id") long id, @RequestBody Camara cam) {
		FactHandle fact = camara_facts.get(id);
		if (fact != null) {
			Camara camara = (Camara) Constante.kSession.getObject(fact);
			camara.setTemperatura(cam.getTemperatura());
			Constante.kSession.update(fact, camara);

			LeituraSensorTemp new_leitura = new LeituraSensorTemp(camara, camara.getTemperatura());
			new_leitura.setId(leitura_ids);
			leitura_ids++;
			leituras_facts.put(new_leitura.getId(), Constante.kSession.insert(new_leitura));

			QueryResults query_lotes = Constante.kSession.getQueryResults(
				"filterLotesByCamaraId", 
				camara.getId()
			);
			for (QueryResultsRow row_lote : query_lotes) {
				Lote lote = (Lote) row_lote.get("lote");
				if (lote.isUtil()) {
					lote.setCamara(camara);
					FactHandle lote_fact = Constante.kSession.getFactHandle(lote);
					Constante.kSession.update(lote_fact, lote);

					QueryResults query_temps = Constante.kSession.getQueryResults(
						"filterTempRuimByLoteId",
						lote.getId()
					);
					for (QueryResultsRow row_temp : query_temps) {
						TempRuim tempRuim = (TempRuim) row_temp.get("tempRuim");
						if(tempRuim.getFim()==null){
							tempRuim.setLote(lote);
							FactHandle temp_fact = Constante.kSession.getFactHandle(tempRuim);
							Constante.kSession.update(temp_fact, tempRuim);
						}
					}
				}
			}
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

// UPDATE
//// ATUALIZA CODIGO DA CAMARA
// Iterable<Lote> lotes = loteRepository.findByCamaraIdAndUtilTrue(id);
//// System.out.println(lotes);
// boolean temp_ruim = false;
// for (Lote lote : lotes) {
// CodigoAlerta codigo = lote.checarTemp();
// if (codigo == CodigoAlerta.MARGEM_MIN || codigo == CodigoAlerta.MARGEM_MAX) {
// temp_ruim = true;
// record.setCodigo(CodigoAlerta.MARGEM_MAX);
// } else if (codigo == CodigoAlerta.TEMP_MIN || codigo ==
// CodigoAlerta.TEMP_MAX) {
// temp_ruim = true;
// record.setCodigo(CodigoAlerta.TEMP_MAX);
// break;
// }
// }
// if (!temp_ruim) {
// record.setCodigo(CodigoAlerta.TEMP_OK);
// }