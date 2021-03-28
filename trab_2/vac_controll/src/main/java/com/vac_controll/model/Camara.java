package com.vac_controll.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;

import com.vac_controll.repository.GestorRepository;

@Entity
public class Camara {
	
	//@Autowired
	//private GestorRepository gestorRepository;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;
	
	@Column(nullable = true)
	private transient FactHandle fact;
	
	@Column(nullable = false)
	private String nome;
	
    @OneToOne
    @JoinColumn(name = "loc_id")
	private Localizacao loc;
	
	@Column(nullable = false)
	private double temperatura;
	
	@Column(nullable = false)
	private double umidade;
	
	public static Camara NOT_FOUND = new Camara("", new Localizacao(0.0,0.0), new ArrayList<Gestor>(), new ArrayList<Lote>(), 0,0);
	
	public Camara(String nome, Localizacao loc, List<Gestor> gestores, List<Lote> lotes, double temperatura, double umidade) {
		super();
		this.nome = nome;
		this.loc = loc;
		this.temperatura = temperatura;
		this.umidade = umidade;
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

	public void setTemperatura(double temperatura) {
		System.out.println("Nova temp: " + temperatura);
		this.temperatura = temperatura;
	}
	
	public double getUmidade() {
		return umidade;
	}

	public void setUmidade(double umidade) {
		this.umidade = umidade;
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
		Camara other = (Camara) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/*
	public void notificarGestores(CodigoAlerta cod, Lote lote) {
		List<Gestor> gestores = gestorRepository.findByCamaraId(this.id);
		for (Gestor gestor : gestores) {
			gestor.enviarMsg(this, lote, cod);
		}
	}
	*/
	
	/*
	public void chamarGestor(CodigoAlerta cod, Lote lote) {
		List<Gestor> gestores = gestorRepository.findByCamaraId(this.id);
		Gestor gMaisProx = this.loc.gestorMaisProx(gestores);
		//System.out.println(lote.getVac().getNome());
		gMaisProx.enviarMsg(this, lote, cod);
	}
	*/
}
