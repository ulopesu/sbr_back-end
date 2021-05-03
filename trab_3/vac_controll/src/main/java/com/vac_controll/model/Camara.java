package com.vac_controll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

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

	public void atualizarCodigo() {
		boolean temp_ruim = false;
		QueryResults query_lotes = Constante.kSession.getQueryResults("filterLotesByCamaraId", this.id);
		for (QueryResultsRow row_lote : query_lotes) {
			Lote lote = (Lote) row_lote.get("lote");
			if (lote.isUtil()) {
				CodigoAlerta codigo = lote.getCodigo();
				if (codigo == CodigoAlerta.MARGEM_MIN || codigo == CodigoAlerta.MARGEM_MAX) {
					temp_ruim = true;
					this.setCodigo(CodigoAlerta.MARGEM_MAX);
				} else if (codigo == CodigoAlerta.TEMP_MIN || codigo == CodigoAlerta.TEMP_MAX) {
					temp_ruim = true;
					this.setCodigo(CodigoAlerta.TEMP_MAX);
				}
			}
		}
		if (!temp_ruim) {
			this.setCodigo(CodigoAlerta.TEMP_OK);
		}

		FactHandle camara_fact = Constante.kSession.getFactHandle(this);
		Constante.kSession.update(camara_fact, this);

		for (QueryResultsRow row_lote : query_lotes) {
			Lote lote = (Lote) row_lote.get("lote");
			if (lote.isUtil()) {
				lote.setCamara(this);
				FactHandle lote_fact = Constante.kSession.getFactHandle(lote);
				Constante.kSession.update(lote_fact, lote);

				QueryResults query_temps = Constante.kSession.getQueryResults("filterTempRuimByLoteId", lote.getId());
				for (QueryResultsRow row_temp : query_temps) {
					TempRuim tempRuim = (TempRuim) row_temp.get("tempRuim");
					if (tempRuim.getFim() == null) {
						tempRuim.setLote(lote);
						FactHandle temp_fact = Constante.kSession.getFactHandle(tempRuim);
						Constante.kSession.update(temp_fact, tempRuim);
					}
				}
			}
		}
	}
}
