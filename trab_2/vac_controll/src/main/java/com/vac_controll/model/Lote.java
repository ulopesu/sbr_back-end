package com.vac_controll.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Lote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true)
	private transient FactHandle fact;
	
	@Column(nullable = false)
	private int qtd;
	
    @ManyToOne
    @JoinColumn(name="camara_id")
	private Camara camara;
	
    @ManyToOne()
    @JoinColumn(name = "vacina_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityReference(alwaysAsId = true)
	private Vacina vacina;
	
	@Column(nullable = false)
	private LocalDate validade;
	
	@Column(nullable = false)
	private boolean util;

	
	public static Lote NOT_FOUND = new Lote(0, Vacina.NOT_FOUND, LocalDate.of(0, 1, 1));
	
	public Lote(int qtd, Vacina vacina, LocalDate validade) {
		super();
		this.qtd = qtd;
		this.vacina = vacina;
		this.validade = validade;
		this.util = true;
	}

    public Lote() {
    }
	
	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	
	
	public Camara getCam() {
		return camara;
	}

	public void setCam(Camara cam) {
		this.camara = cam;
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
	
	public FactHandle getFact() {
		return fact;
	}

	public void setFact(FactHandle fact) {
		this.fact = fact;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lote other = (Lote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void updatekSession(KieSession kSession) {
		kSession.update(this.fact, this);
		this.vacina.updatekSession(kSession);
	}

	public CodigoAlerta checarTempLimiar() {
		if(this.vacina.getTempMax() >= this.camara.getTemperatura() && (this.vacina.getTempMax()-this.vacina.getTemp_margem()) <= this.camara.getTemperatura()) {
			return CodigoAlerta.MARGEM_MAX;
		} else if (this.vacina.getTempMin() <= this.camara.getTemperatura() && (this.vacina.getTempMin()+this.vacina.getTemp_margem()) >= this.camara.getTemperatura()) {
			return CodigoAlerta.MARGEM_MIN;
		} else {
			return CodigoAlerta.TEMP_OK;
		}
	}
	

	public CodigoAlerta checarTempRuim() {
		if(this.vacina.getTempMax() <= this.camara.getTemperatura()) {
			return CodigoAlerta.TEMP_MAX;
		} else if (this.vacina.getTempMin() >= this.camara.getTemperatura()) {
			return CodigoAlerta.TEMP_MIN;
		} else {
			return CodigoAlerta.TEMP_OK;
		}
	}
}
