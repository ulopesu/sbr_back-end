package com.vac_controll.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;


@Entity
public class Descarte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private Date data;

	@OneToOne
	@JoinColumn(name = "lote_id")
	private Lote lote;

	@Column(nullable = false)
	private CodigoAlerta codigo;

	public Descarte(Date data, Lote lote, CodigoAlerta codigo) {
		super();
		this.data = data;
		this.lote = lote;
		this.codigo = codigo;
	}

	public Descarte() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public CodigoAlerta getCodigo() {
		return codigo;
	}

	public void setCodigo(CodigoAlerta codigo) {
		this.codigo = codigo;
	}

}
