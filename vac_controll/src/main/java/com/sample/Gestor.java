package com.sample;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class Gestor {
	private Camara cam;
	private FactHandle fact;
	private String nome;
	private String email;
	private String telefone;
	private Localizacao loc;

	public static Gestor NOT_FOUND = new Gestor("Gestor Nulo", "", "", new Localizacao(0.0, 0.0));

	public Gestor(String nome, String email, String telefone, Localizacao loc) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.loc = loc;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Localizacao getLoc() {
		return loc;
	}

	public void setLoc(Localizacao loc) {
		this.loc = loc;
	}

	public FactHandle getFact() {
		return fact;
	}

	public void setFact(FactHandle fact) {
		this.fact = fact;
	}

	public Camara getCam() {
		return cam;
	}

	public void setCam(Camara cam) {
		this.cam = cam;
	}

	public void updatekSession(KieSession kSession) {
		kSession.update(this.fact, this);
		this.cam.updatekSession(kSession);
	}

	public void enviarMsg(Camara cam, int check) {
		switch (check) {
		case 1:
			System.out.println(	"ALERTA -> Temperatura da Camara("+cam.getNome()+") "
								+ "está próxima do limite negativo.");
			break;

		case 2:
			System.out.println(	"ALERTA -> Temperatura da Camara("+cam.getNome()+") "
								+ "está próxima do limite positivo.");
			break;

		case 4:
			System.out.println(	"ALERTA -> Temperatura da Camara("+cam.getNome()+") "
								+ "ultrapassou limite negativo. " +this.nome+ " dirija-se ao local.");
			break;

		case 5:
			System.out.println(	"ALERTA -> Temperatura da Camara("+cam.getNome()+") "
					+ "ultrapassou limite positivo. " +this.nome+ " dirija-se ao local.");
			break;

		default:
			// code block
		}

	}

}
