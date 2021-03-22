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
        	gestor1.setFact(kSession.insert(gestor1));
        	
        	Gestor gestor2 = new Gestor(
				"Usi", 
				"usi@ufes.br", 
				"1236478", 
				new Localizacao(-20.352434313227818, -40.385297697254835)
			);
        	gestor2.setFact(kSession.insert(gestor2));

        	Vacina vac_1 = new Vacina(
				"corona vac", 
				12, 
				5,
				20
			);
        	vac_1.setFact(kSession.insert(vac_1));
        	
        	Vacina vac_2 = new Vacina(
				"sputnik v", 
				18, 
				5,
				30
			);
        	vac_2.setFact(kSession.insert(vac_2));
        	
        	
        	Lote lote1 = new Lote(20, vac_1, LocalDate.of(2022, 01, 15));
        	lote1.setFact(kSession.insert(lote1));
        	
        	//Lote lote2 = new Lote(10, vac_2, LocalDate.of(2022, 01, 15));
        	//lote2.setFact(kSession.insert(lote2));
            
            Camara cam_1 = new Camara(
            	"Cariacica",
	    		new Localizacao(-20.34900255555123, -40.3901474607013),
	    		Arrays.asList(gestor1, gestor2),
	    		Arrays.asList(lote1), 
	    		0,
	    		0
	    	);
            cam_1.setFact(kSession.insert(cam_1));
            
            
			//TODO: ENTRADA DE DADOS POR ARQUIVO (URL)
            String url = "https://api-temp-umid.herokuapp.com/";
            Thread t1 = new Thread(new TempCamaraWrapper(kSession, cam_1, url));
            t1.start();
            
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

