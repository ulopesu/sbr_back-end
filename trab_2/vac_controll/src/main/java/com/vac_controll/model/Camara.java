package com.vac_controll.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

@Entity
public class Camara {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;
	
	@Column(nullable = true)
	private transient FactHandle fact;
	
	@Column(nullable = false)
	private String nome;
	
	@ManyToOne
	private Localizacao loc;
	
    @OneToMany(mappedBy = "camara")
    @Column(nullable = false)
	private List<Gestor> gestores;
	
	@OneToMany(mappedBy = "camara")
	@Column(nullable = false)
	private List<Lote> lotes;
	
	@Column(nullable = false)
	private double temperatura;
	
	@Column(nullable = false)
	private double umidade;
	
	public static Camara NOT_FOUND = new Camara("", new Localizacao(0.0,0.0), new ArrayList<Gestor>(), new ArrayList<Lote>(), 0,0);
	
	public Camara(String nome, Localizacao loc, List<Gestor> gestores, List<Lote> lotes, double temperatura, double umidade) {
		super();
		this.nome = nome;
		this.loc = loc;
		this.gestores = gestores;
		this.lotes = lotes;
		this.temperatura = temperatura;
		this.umidade = umidade;
		
		for (Lote lote : this.lotes) {
			lote.setCam(this);
		}
		
		for (Gestor gestor : this.gestores) {
			gestor.setCam(this);
		}
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
	
	
	public List<Gestor> getGestores() {
		return gestores;
	}

	public void setGestores(List<Gestor> gestores) {
		this.gestores = gestores;
	}

	public List<Lote> getLotes() {
		return lotes;
	}

	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
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

	public void updatekSession(KieSession kSession) {
		kSession.update(this.fact, this);
		
		for (Lote lote : this.lotes) {
			lote.updatekSession(kSession);
		}
	}
	
	public void notificarGestores(CodigoAlerta cod, Lote lote) {
		for (Gestor gestor : this.gestores) {
			gestor.enviarMsg(this, lote, cod);
		}
	}
	
	public void chamarGestor(CodigoAlerta cod, Lote lote) {
		Gestor gMaisProx = this.loc.gestorMaisProx(this.gestores);
		//System.out.println(lote.getVac().getNome());
		gMaisProx.enviarMsg(this, lote, cod);
	}
	
}
