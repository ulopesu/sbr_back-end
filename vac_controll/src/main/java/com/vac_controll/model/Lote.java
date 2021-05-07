package com.vac_controll.model;

import java.time.LocalDate;
import java.util.List;

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

	public Lote(int qtd, Vacina vacina, Camara camara, LocalDate validade) {
		super();
		this.qtd = qtd;
		this.vacina = vacina;
		this.camara = camara;
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

	public static int compareById(Lote l1, Lote l2){
		return l2.getId().compareTo(l1.getId());
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

	public void notificarGestores(CodigoAlerta cod, List<Gestor> gestores) {
		Gestor gMaisProx = this.getCamara().getLoc().gestorMaisProx(gestores);

		for (Gestor gestor : gestores) {
			if (gestor == gMaisProx) {
				gestor.enviarMsg(this, cod, true);
			} else {
				gestor.enviarMsg(this, cod, false);
			}
		}
	}
}
