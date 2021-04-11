package com.vac_controll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vac_controll.model.TempRuim;
import com.vac_controll.repository.TempRuimRepository;

@RestController
@RequestMapping("/temp_ruim")
public class TempRuimController {
	@Autowired
	private TempRuimRepository tempRuimRepository;
	
	@GetMapping
	public Iterable<TempRuim> list() {
		return tempRuimRepository.findAll();
	}
}
