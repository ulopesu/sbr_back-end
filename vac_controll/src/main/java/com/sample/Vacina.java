package com.sample;

import java.time.LocalDate;

public class Vacina {
	private String nome;
	private double tempMax;
	private double tempMin;
	private LocalDate validade;
	private boolean util;
	
	public Vacina(String nome, double tempMax, double tempMin, LocalDate validade, boolean util) {
		super();
		this.nome = nome;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
		this.validade = validade;
		this.util = util;
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
	
	public LocalDate getValidade() {
		return validade;
	}
	
	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}
	
	public boolean isUtil() {
		return util;
	}
	
	public void setUtil(boolean util) {
		this.util = util;
	}
	
}
