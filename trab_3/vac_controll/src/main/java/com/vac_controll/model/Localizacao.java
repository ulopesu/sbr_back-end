package com.vac_controll.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Localizacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Double longitude;
	
	@Column(nullable = false)
	private Double latitude;
	
	public Localizacao(Double longitude, Double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Localizacao() {
		
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public Gestor gestorMaisProx(List<Gestor> gestores) {
		Boolean inicio = true;
		Double menorDist = 0.0;
		Gestor gMaisProx = Gestor.NOT_FOUND;
		
		for (Gestor gestor : gestores) {
			Double distLat = Math.pow(this.latitude - gestor.getLoc().getLatitude(), 2);
			Double distLog = Math.pow(this.longitude - gestor.getLoc().getLongitude(), 2);
			Double dist = Math.sqrt(distLat + distLog);
			if (inicio) {
				menorDist = dist;
				gMaisProx = gestor;
				inicio = false;
			}
			if(dist < menorDist) {
				menorDist = dist;
				gMaisProx = gestor;
			}
		}
		return gMaisProx;
	}
	
}
