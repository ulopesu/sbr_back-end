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

import com.fasterxml.jackson.annotation.JsonIdentityReference;

@Entity
public class Lote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private int qtd;

	@ManyToOne
	@JoinColumn(name = "camara_id")
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

	@Column(nullable = true)
	public CodigoAlerta codigo;

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

	public Camara getCamara() {
		return camara;
	}

	public void setCamara(Camara camara) {
		this.camara = camara;
		this.checarTemp();
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

	public CodigoAlerta getCodigo() {
		return this.codigo;
	}

	public void setCodigo(CodigoAlerta codigo) {
		this.codigo = codigo;
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

	public CodigoAlerta checarTemp() {
		double temp_cam = this.camara.getTemperatura();
		double temp_margem_vac = this.vacina.getTemp_margem();
		double temp_min_vac = this.vacina.getTempMin();
		double temp_max_vac = this.vacina.getTempMax();

		if (temp_max_vac <= temp_cam) {
			this.codigo = CodigoAlerta.TEMP_MAX;
			return CodigoAlerta.TEMP_MAX;

		} else if (temp_min_vac >= temp_cam) {

			this.codigo = CodigoAlerta.TEMP_MIN;
			return CodigoAlerta.TEMP_MIN;

		} else if (temp_max_vac >= temp_cam && (temp_max_vac - temp_margem_vac) <= temp_cam) {

			this.codigo = CodigoAlerta.MARGEM_MAX;
			return CodigoAlerta.MARGEM_MAX;

		} else if (temp_min_vac <= temp_cam && (temp_min_vac + temp_margem_vac) >= temp_cam) {

			this.codigo = CodigoAlerta.MARGEM_MIN;
			return CodigoAlerta.MARGEM_MIN;

		} else {
			this.codigo = CodigoAlerta.TEMP_OK;
			return CodigoAlerta.TEMP_OK;
		}
	}

	public void atualizarCam() {
		this.checarTemp();
		CodigoAlerta codigo = this.camara.getCodigo();

		if ((this.codigo == CodigoAlerta.TEMP_MAX || this.codigo == CodigoAlerta.TEMP_MAX)
				&& codigo != CodigoAlerta.TEMP_MAX) {
			this.camara.setCodigo(CodigoAlerta.TEMP_MAX);
		} else if ((this.codigo == CodigoAlerta.MARGEM_MAX || this.codigo == CodigoAlerta.MARGEM_MIN)
				&& codigo == CodigoAlerta.TEMP_OK) {
			this.camara.setCodigo(CodigoAlerta.MARGEM_MAX);
		}
	}
}
