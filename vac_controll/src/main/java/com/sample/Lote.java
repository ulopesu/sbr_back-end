package com.sample;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Lote {
	private int qtd;
	private Camara cam;
	private Vacina vac;
	private LocalDate validade;
	private boolean util;
	private boolean ehTempLimiar;
	private boolean ehTempRuim;

	
	public static Lote NOT_FOUND = new Lote(0, Vacina.NOT_FOUND, LocalDate.of(0, 1, 1), false);
	
	public Lote(int qtd, Vacina vac, LocalDate validade, boolean util) {
		super();
		this.qtd = qtd;
		this.vac = vac;
		this.validade = validade;
		this.util = util;
		this.ehTempLimiar = false;
		this.ehTempRuim = false;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	
	
	public Camara getCam() {
		return cam;
	}

	public void setCam(Camara cam) {
		this.cam = cam;
	}

	public Vacina getVac() {
		return vac;
	}

	public void setVac(Vacina vac) {
		this.vac = vac;
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
	
	public boolean isEhTempLimiar() {
		return ehTempLimiar;
	}

	public void setEhTempLimiar(boolean ehTempLimiar) {
		this.ehTempLimiar = ehTempLimiar;
	}

	public boolean isEhTempRuim() {
		return ehTempRuim;
	}

	public void setEhTempRuim(boolean ehTempRuim) {
		this.ehTempRuim = ehTempRuim;
	}

	
	void checarTempLimiar() {
		if((this.vac.getTempMin() < this.cam.getTemperatura() && (this.vac.getTempMin()+2) > this.cam.getTemperatura()) || 
		   (this.vac.getTempMax() > this.cam.getTemperatura() && (this.vac.getTempMax()-2) < this.cam.getTemperatura())) {
			System.out.println("\nchecarTempLimiar: " +this.vac.getNome());
			this.ehTempLimiar = true;
		} else {
			this.ehTempLimiar = false;
		}
	}
	
	void checarTempRuim() {
		if(this.vac.getTempMax() < this.cam.getTemperatura()|| this.vac.getTempMin() > this.cam.getTemperatura()) {
			System.out.println("\nchecarTempRuim: " +this.vac.getNome());
			this.ehTempRuim = true;
		} else {
			this.ehTempRuim = false;
		}
	}

}
