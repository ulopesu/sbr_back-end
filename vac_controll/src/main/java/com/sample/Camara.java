package com.sample;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class Camara implements Cloneable {
	private FactHandle fact;
	private String nome;
	private Localizacao loc;
	private List<Gestor> gestores;
	private List<Lote> lotes;
	private double temperatura;
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
	
	
	public void updatekSession(KieSession kSession) {
		kSession.update(this.fact, this);
		
		for (Lote lote : this.lotes) {
			lote.updatekSession(kSession);
		}
	}
	
	
	
	public Object clone() throws CloneNotSupportedException {		
        return super.clone();
    }
	
	
	public void notificarGestores(int check) {
		for (Gestor gestor : this.gestores) {
			gestor.enviarMsg(this, check);
		}
	}
	
	public void chamarGestor(int check) {
		Gestor gMaisProx = this.loc.gestorMaisProx(this.gestores);
		
		//TODO: ENVIAR
		gMaisProx.enviarMsg(this, check);
	}
	
}
