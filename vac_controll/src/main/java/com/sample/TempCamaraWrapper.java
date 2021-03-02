package com.sample;

import java.util.Random;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import com.sun.xml.internal.ws.api.addressing.AddressingVersion.EPR;

public class TempCamaraWrapper implements Runnable {
	KieSession kSession;
	FactHandle fact;
	
	
	
	public TempCamaraWrapper(KieSession kSession, FactHandle fact) {
		super();
		this.kSession = kSession;
		this.fact = fact;
	}
	
	


	@Override
	public void run() {
		while(true) {
			try {
				Random rand = new Random();
				Double valor = 5.0 + rand.nextInt(21);
				
				Camara cam = (Camara) kSession.getObject(fact);
				cam.setTemperatura(valor);
				
				kSession.update(fact, cam);
				kSession.fireAllRules();
				
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		
	}

}
