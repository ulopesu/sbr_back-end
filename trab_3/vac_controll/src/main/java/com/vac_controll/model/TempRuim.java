package com.vac_controll.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

@Entity
public class TempRuim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "lote_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIdentityReference(alwaysAsId = true)
	private Lote lote;

	@Column(nullable = false)
	private double temperatura;

	@Column(nullable = false)
	private Date inicio;

	@Column(nullable = true)
	private Date fim;

	@Column(nullable = false)
	private CodigoAlerta codigo;

	public TempRuim(Lote lote, double temperatura, Date inicio, CodigoAlerta codigo) {
		super();
		this.lote = lote;
		this.temperatura = temperatura;
		this.inicio = inicio;
		this.fim = null;
		this.codigo = codigo;
	}

	public TempRuim() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public CodigoAlerta getCodigo() {
		return this.codigo;
	}
	
	public void setCodigo(CodigoAlerta codigo) {
		this.codigo = codigo;
	}
}
