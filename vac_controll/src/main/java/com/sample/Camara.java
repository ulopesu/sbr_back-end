package com.sample;

import java.util.List;

public class Camara implements Cloneable {
	private String nome;
	private Localizacao loc;
	private List<Gestor> gestores;
	private List<Vacina> vacinas;
	private double temperatura;
	
	public Camara(String nome, Localizacao loc, List<Gestor> gestores, List<Vacina> vacinas, double temperatura) {
		super();
		this.nome = nome;
		this.loc = loc;
		this.gestores = gestores;
		this.vacinas = vacinas;
		this.temperatura = temperatura;
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

	public List<Vacina> getVacinas() {
		return vacinas;
	}

	public void setVacinas(List<Vacina> vacinas) {
		this.vacinas = vacinas;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		System.out.println("Nova temp: " + temperatura);
		this.temperatura = temperatura;
	}
	
	
	public Object clone() throws CloneNotSupportedException {		
        return super.clone();
    }
	
	
	// TODO
	/*
		FUNCOES:
		NOTIFICAR_GESTORES

		CHAMAR_GESTO_MAIS_PROX
	 */
	
	public void chamarGestor() {
		Gestor gMaisProx = this.loc.gestorMaisProx(this.gestores);
		
		//TODO: ENVIAR
		gMaisProx.enviarMsg(this);
	}
	
}
