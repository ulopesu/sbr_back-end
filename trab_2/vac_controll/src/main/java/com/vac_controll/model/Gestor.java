package com.vac_controll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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

	private String mensagem(Lote lote, CodigoAlerta cod, boolean ehMaixProx) {
		String msg = null;
		switch (cod) {
		case MARGEM_MIN:
			msg = "\nALERTA -> Temperatura da " + lote.getCamara().getNome()
					+ " está próxima do limite mínimo.\n";
			break;

		case MARGEM_MAX:
			msg = "\nALERTA -> Temperatura da " + lote.getCamara().getNome()
					+ " está próxima do limite máximo.\n";
			break;

		case TEMP_MIN:
			msg = "\nALERTA -> Temperatura da " + lote.getCamara().getNome()
					+ " ultrapassou limite mínimo.\n";
			break;

		case TEMP_MAX:
			msg = "\nALERTA -> Temperatura da " + lote.getCamara().getNome()
					+ " ultrapassou limite máximo.\n";
			break;

		case DESCARTE:
			msg = "\n" + "ALERTA -> Descarte na " + lote.getCamara().getNome() + ":\n"
					+ "Vacina: " + lote.getVacina().getNome() + "\nValidade: " + lote.getValidade() + "\nQuantidade: "
					+ lote.getQtd() + ".\n";
			break;
		default:
		}

		if (ehMaixProx && cod!=CodigoAlerta.DESCARTE) {
			msg += this.nome +", você é o gestor mais próximo, dirija-se ao local!!!\n";
		}
		return msg;
	}

	public void enviarMsg(Lote lote, CodigoAlerta cod, boolean ehMaixProx) {
		System.out.println(this.mensagem(lote, cod, ehMaixProx));
	}

}
