package com.vac_controll.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vac_controll.model.HistoricoCamara;
import com.vac_controll.repository.HistoricoCamaraRepository;


@RestController
@RequestMapping("/historico_camara")
public class HistoricoCamaraController {
	
	@Autowired
	private HistoricoCamaraRepository historicoCamaraRepository;
	
	@GetMapping(value="/{camara_id}")
	public List<HistoricoCamara> list(@PathVariable("camara_id") long id) {
		return historicoCamaraRepository.findByCamaraId(id);
	}
	
}
