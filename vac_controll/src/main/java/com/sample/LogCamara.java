package com.sample;

import java.util.Date;

public class LogCamara {
	private Date data;
	private final Camara camara;
	
	public LogCamara (Date data, Camara camara) throws CloneNotSupportedException {
		super();
		this.data = data;
		this.camara = (Camara) camara.clone();
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Camara getCamara() {
		return camara;
	} 
	
}