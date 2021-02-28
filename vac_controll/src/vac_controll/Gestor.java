package vac_controll;

import vac_controll.Localizacao;

public class Gestor {
	private String nome;
	private String email;
	private String telefone;
	private Localizacao loc;
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
}
