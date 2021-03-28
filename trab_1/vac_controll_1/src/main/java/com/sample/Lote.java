package com.sample;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class Lote {
	private transient FactHandle fact;
	private int qtd;
	private Camara cam;
	private Vacina vac;
	private LocalDate validade;
	private boolean util;
	private boolean ehTempLimiar;
	private boolean ehTempRuim;

	
	public static Lote NOT_FOUND = new Lote(0, Vacina.NOT_FOUND, LocalDate.of(0, 1, 1));
	
	public Lote(int qtd, Vacina vac, LocalDate validade) {
		super();
		this.qtd = qtd;
		this.vac = vac;
		this.validade = validade;
		this.util = true;
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

	
	public CodigoAlerta checarTempLimiar() {
		if(this.vac.getTempMax() >= this.cam.getTemperatura() && (this.vac.getTempMax()-this.vac.getTemp_margem()) <= this.cam.getTemperatura()) {
			return CodigoAlerta.MARGEM_MAX;
		} else if (this.vac.getTempMin() <= this.cam.getTemperatura() && (this.vac.getTempMin()+this.vac.getTemp_margem()) >= this.cam.getTemperatura()) {
			return CodigoAlerta.MARGEM_MIN;
		} else {
			return CodigoAlerta.TEMP_OK;
		}
	}
	

	
	public CodigoAlerta checarTempRuim() {
		if(this.vac.getTempMax() <= this.cam.getTemperatura()) {
			return CodigoAlerta.TEMP_MAX;
		} else if (this.vac.getTempMin() >= this.cam.getTemperatura()) {
			return CodigoAlerta.TEMP_MIN;
		} else {
			return CodigoAlerta.TEMP_OK;
		}
	}


}
