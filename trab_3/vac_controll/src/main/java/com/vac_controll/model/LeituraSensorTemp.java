package com.vac_controll.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Entity
@Role(Role.Type.EVENT)
@Expires ("30d")
public class LeituraSensorTemp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = true)
	private Date data;

    @Column(nullable = true)
    double timemilli;

	@OneToOne
	@JoinColumn(name = "camara_id")
	private Camara camara;

    @Column(nullable = false)
	private double temperatura;

    @Column(nullable = true)
	private boolean atual;


    public LeituraSensorTemp(Camara camara, double temperatura) {
		super();
        this.camara = camara;
		this.temperatura = temperatura;
		this.data = new Date();
		this.timemilli = this.data.getTime();
        this.atual = true;
    }

    public LeituraSensorTemp() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Camara getCamara() {
        return this.camara;
    }

    public void setCamara(Camara camara) {
        this.camara = camara;
    }

    public double getTemperatura() {
        return this.temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getTimemilli() {
        return this.timemilli;
    }

    public void setTimemilli(double timemilli) {
        this.timemilli = timemilli;
    }

    public boolean isAtual() {
        return this.atual;
    }

    public void setAtual(boolean atual) {
        this.atual = atual;
    }

}
