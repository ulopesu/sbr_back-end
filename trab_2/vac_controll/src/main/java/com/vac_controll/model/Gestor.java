package com.vac_controll.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Gestor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "camara_id")
	private Camara camara;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String telefone;

    @OneToOne
    @JoinColumn(name = "loc_id")
	private Localizacao loc;
    
    public static Gestor NOT_FOUND = new Gestor();
	
	public Gestor(String nome, String email, String telefone, Localizacao loc) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.loc = loc;
	}
	
    public Gestor() {
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

	public Camara getCamara() {
		return camara;
	}

	public void setCamara(Camara camara) {
		this.camara = camara;
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
		Gestor other = (Gestor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void enviarMsg(Lote lote, CodigoAlerta cod) {
		switch (cod) {
		case MARGEM_MIN:
			System.out.println("\nALERTA -> Temperatura da Camara(" + lote.getCamara().getNome()
					+ ") está próxima do limite negativo.\n");
			break;

		case MARGEM_MAX:
			System.out.println("\nALERTA -> Temperatura da Camara(" + lote.getCamara().getNome()
					+ ") está próxima do limite positivo.\n");
			break;

		case TEMP_MIN:
			System.out.println("\nALERTA -> Temperatura da Camara(" + lote.getCamara().getNome()
					+ ") ultrapassou limite negativo. " + this.nome + " dirija-se ao local.\n");
			break;

		case TEMP_MAX:
			System.out.println("\nALERTA -> Temperatura da Camara(" + lote.getCamara().getNome()
					+ ") ultrapassou limite positivo. " + this.nome + " dirija-se ao local.\n");
			break;

		case DESCARTE_MIN:
			System.out.println("\n" + this.nome + ",\n" + "ALERTA -> Descarte na Camara(" + lote.getCamara().getNome()
					+ "):\n" + "Vacina: " + lote.getVacina().getNome() + "\nValidade: " + lote.getValidade()
					+ "\nQuantidade: " + lote.getQtd() + ".\n");
			break;

		case DESCARTE_MAX:
			System.out.println("\n" + this.nome + ",\n" + "ALERTA -> Descarte na Camara(" + lote.getCamara().getNome()
					+ "):\n" + "Vacina: " + lote.getVacina().getNome() + ".\nValidade: " + lote.getValidade()
					+ ".\nQuantidade: " + lote.getQtd() + ".\n");
			break;
		default:
			// code block
		}

	}

}
