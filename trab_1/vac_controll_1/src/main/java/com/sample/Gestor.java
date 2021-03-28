package com.sample;

import java.sql.Date;
import java.time.LocalDate;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class Gestor {
	private Camara cam;
	private transient FactHandle fact;
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

	public void enviarMsg(Camara cam, Lote lote, CodigoAlerta cod) {
		switch (cod) {
		case MARGEM_MIN:
			System.out.println(	"\nALERTA -> Temperatura da Camara("+cam.getNome()+") "
								+ "está próxima do limite negativo.\n");
			break;

		case MARGEM_MAX:
			System.out.println(	"\nALERTA -> Temperatura da Camara("+cam.getNome()+") "
								+ "está próxima do limite positivo.\n");
			break;

		case TEMP_MIN:
			System.out.println(	"\nALERTA -> Temperatura da Camara("+cam.getNome()+") "
								+ "ultrapassou limite negativo. " +this.nome+ " dirija-se ao local.\n");
			break;

		case TEMP_MAX:
			System.out.println(	"\nALERTA -> Temperatura da Camara("+cam.getNome()+") "
					+ "ultrapassou limite positivo. " +this.nome+ " dirija-se ao local.\n");
			break;
	
		case DESCARTE_MIN:
			System.out.println(
					"\n" + this.nome +",\n" +
					"ALERTA -> Descarte na Camara("+cam.getNome()+"):\n" +
					"Vacina: "+ lote.getVac().getNome() +
					"\nValidade: " + lote.getValidade() +
					"\nQuantidade: " + lote.getQtd() + ".\n"
					);
			break;

		case DESCARTE_MAX:
			System.out.println(	
					"\n" + this.nome +",\n" +
					"ALERTA -> Descarte na Camara("+cam.getNome()+"):\n" +
					"Vacina: "+ lote.getVac().getNome() +
					".\nValidade: " + lote.getValidade() +
					".\nQuantidade: " + lote.getQtd() + ".\n"
					);
			break;
		default:
			// code block
		}

	}

}
