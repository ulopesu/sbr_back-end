package com.sample;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class Lote {
	private FactHandle fact;
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
	
	public FactHandle getFact() {
		return fact;
	}

	public void setFact(FactHandle fact) {
		this.fact = fact;
	}

	public void updatekSession(KieSession kSession) {
		kSession.update(this.fact, this);
		this.vac.updatekSession(kSession);
	}
	/*
	public boolean checarTempLimiar() {
		if((this.vac.getTempMin() < this.cam.getTemperatura() && (this.vac.getTempMin()+2) > this.cam.getTemperatura()) || 
		   (this.vac.getTempMax() > this.cam.getTemperatura() && (this.vac.getTempMax()-2) < this.cam.getTemperatura())) {
			this.ehTempLimiar = true;
			return true;
		} else {
			this.ehTempLimiar = false;
			return false;
		}
	}
	*/
	public int checarTempLimiar() {
		if(this.vac.getTempMax() > this.cam.getTemperatura() && (this.vac.getTempMax()-2) < this.cam.getTemperatura()) {
			return 2;
		} else if (this.vac.getTempMin() < this.cam.getTemperatura() && (this.vac.getTempMin()+2) > this.cam.getTemperatura()) {
			return 1;
		} else {
			return 3;
		}
	}
	
	/*
	public boolean checarTempRuim() {
		if(this.vac.getTempMax() <= this.cam.getTemperatura() || this.vac.getTempMin() >= this.cam.getTemperatura()) {
			this.ehTempRuim = true;
			return true;
		} else {
			this.ehTempRuim = false;
			return false;
		}
	}
	*/
	
	public int checarTempRuim() {
		if(this.vac.getTempMax() < this.cam.getTemperatura()) {
			return 5;
		} else if (this.vac.getTempMin() > this.cam.getTemperatura()) {
			return 4;
		} else {
			return 6;
		}
	}


}
