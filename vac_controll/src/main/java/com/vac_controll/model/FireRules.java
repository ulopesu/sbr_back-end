package com.vac_controll.model;

public class FireRules implements Runnable {

	public FireRules() {
		super();
	}

	public void run() {
		while (true) {
			try {
				Constante.kSession.fireAllRules();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static long tempoAtual(){
		return System.currentTimeMillis();
	}
}
