package com.sample;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 * This is a sample class to launch a rule.
 */
public class ProcessDrools {

	public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");

            // go ! 
        	Gestor gestor1 = new Gestor(
    			"Isa", 
    			"isa@ufes.br", 
    			"1236478", 
    			new Localizacao(-20.338067121605047, -40.36906294598647)
    		);
        	kSession.insert(gestor1);
        	
        	Gestor gestor2 = new Gestor(
				"Usi", 
				"usi@ufes.br", 
				"1236478", 
				new Localizacao(-20.352434313227818, -40.385297697254835)
			);
        	kSession.insert(gestor2);

        	Vacina vac_1 = new Vacina(
				"corona vac", 
				15, 
				5, 
				LocalDate.of(2022, 06, 01),
				true
			);
        	FactHandle conona_vac = kSession.insert(vac_1);
        	
        	Vacina vac_2 = new Vacina(
				"sputnik v", 
				10, 
				3, 
				LocalDate.of(2022, 01, 01),
				true
			);
        	FactHandle sputnik_v = kSession.insert(vac_2);
            
            Camara cam_1 = new Camara(
            	"Cariacica",
	    		new Localizacao(-20.33279537493192, -40.38528979403148),
	    		Arrays.asList(gestor1, gestor2),
	    		Arrays.asList(vac_1, vac_2), 
	    		0
	    	);
            FactHandle cam_Cariacica = kSession.insert(cam_1);
            
            Thread t1 = new Thread(new TempCamaraWrapper(kSession, cam_Cariacica));
            t1.start();
            
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

