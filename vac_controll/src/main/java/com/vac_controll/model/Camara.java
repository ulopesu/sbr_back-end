package com.vac_controll.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.kie.api.runtime.rule.FactHandle;

@Entity
public class Camara {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@OneToOne
	@JoinColumn(name = "loc_id")
	private Localizacao loc;

	@Column(nullable = false)
	private double temperatura;

	@Column(nullable = true)
	private CodigoAlerta codigo;

	@Column(nullable = true)
	private boolean alertaDefeito;

	public Camara(String nome, Localizacao loc, double temperatura) {
		super();
		this.nome = nome;
		this.loc = loc;
		this.temperatura = temperatura;
		this.codigo = CodigoAlerta.TEMP_OK;
		this.alertaDefeito = false;
	}

	public Camara() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Localizacao getLoc() {
		return loc;
	}

	public void setLoc(Localizacao loc) {
		this.loc = loc;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public synchronized void setTemperatura(double temperatura) {
		System.out.println("Nova temp: " + temperatura);
		this.temperatura = temperatura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CodigoAlerta getCodigo() {
		return this.codigo;
	}

	public synchronized void setCodigo(CodigoAlerta codigo) {
		this.codigo = codigo;
	}

	public void setAlertaDefeito(boolean alertaDefeito) {
		this.alertaDefeito = alertaDefeito;
	}

	public boolean getAlertaDefeito() {
		return this.alertaDefeito;
	}

	public void setLotes(List<Lote> lotes, List<TempRuim> tempsRuins) {
		FactHandle lote_fact, temp_fact;

		for(Lote lote : lotes){
			if(lote.isUtil()) {
				lote_fact = Constante.kSession.getFactHandle(lote);
				lote.setCamara(this);
				Constante.kSession.update(lote_fact, lote);
	
				// ATUALIZAR TEMPS RUINS DOS LOTES
				for(TempRuim temp : tempsRuins){
					if(	temp.getLote().isUtil() &&
						temp.getLote().getId() == lote.getId()
					){
						temp.setLote(lote);
						temp_fact = Constante.kSession.getFactHandle(temp);
						Constante.kSession.update(temp_fact, temp);
					}
				}
			}
		}
	}
}
