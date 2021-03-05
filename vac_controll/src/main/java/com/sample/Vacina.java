package com.sample;

import java.time.LocalDate;

public class Vacina {
	private String nome;
	private double tempMax;
	private double tempMin;
	
	public static Vacina NOT_FOUND = new Vacina("", 0, 0);

	
	public Vacina(String nome, double tempMax, double tempMin) {
		super();
		this.nome = nome;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
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

}
