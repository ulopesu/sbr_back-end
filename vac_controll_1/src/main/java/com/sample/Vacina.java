package com.sample;

import java.time.LocalDate;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class Vacina {
	private transient FactHandle fact;
	private String nome;
	private double tempMax;
	private double tempMin;
	private double margem; // Porcentagem da temperatura de risco,
							// baseada na temperatura maxima.
							// Ex: tempMax = 30 e margem = 10 produz uma
							// temperatura de seguranca de 3 graus.

	public static Vacina NOT_FOUND = new Vacina("", 0, 0, 0);

	public Vacina(String nome, double tempMax, double tempMin, double margemSeguranca) {
		super();
		this.nome = nome;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
		this.margem = margemSeguranca;
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

	public FactHandle getFact() {
		return fact;
	}

	public void setFact(FactHandle fact) {
		this.fact = fact;
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

	public void updatekSession(KieSession kSession) {
		kSession.update(this.fact, this);
	}

}
