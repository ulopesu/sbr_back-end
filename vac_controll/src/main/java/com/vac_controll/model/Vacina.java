package com.vac_controll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vacina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private double tempMax;
	
	@Column(nullable = false)
	private double tempMin;
	
	@Column(nullable = false)
	private double margem;
							// Porcentagem da temperatura de risco,
							// baseada na temperatura maxima.
							// Ex: tempMax = 30 e margem = 10 produz uma
							// temperatura de seguranca de 3 graus.

	public Vacina(String nome, double tempMax, double tempMin, double margemSeguranca) {
		super();
		this.nome = nome;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
		this.margem = margemSeguranca;
	}
	
    public Vacina() {
    }
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	public double getMargem() {
		return margem;
	}

	public void setMargem(double margem) {
		this.margem = margem;
	}

	public double getTemp_margem() {
		return margem * tempMax / 100;

	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
