package com.vac_controll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VacControllApplication {
	public static final void main(String[] args) {
		
		SpringApplication.run(VacControllApplication.class, args);

	}
}


/*
 * ANTIGA MAIN
 * 
 * 
 * try {
 * 
 * KieServices ks = KieServices.Factory.get(); 
 * KieContainer kContainer = ks.getKieClasspathContainer(); 
 * KieSession kSession = kContainer.newKieSession("ksession-rules");
 * 
 * // go ! Gestor gestor1 = new Gestor( "Isa", "isa@ufes.br", "1236478", new
 * Localizacao(-20.338067121605047, -40.36906294598647) );
 * gestor1.setFact(kSession.insert(gestor1));
 * 
 * Gestor gestor2 = new Gestor( "Usi", "usi@ufes.br", "1236478", new
 * Localizacao(-20.352434313227818, -40.385297697254835) );
 * gestor2.setFact(kSession.insert(gestor2));
 * 
 * Gson gson = new Gson();
 * 
 * 
 * Vacina[] vacs = null;
 * 
 * try {
 * 
 * BufferedReader br = new BufferedReader(new FileReader("import.json"));
 * 
 * vacs = gson.fromJson(br, Vacina[].class);
 * 
 * 
 * } catch (IOException e) { e.printStackTrace(); }
 * 
 * 
 * 
 * vacs[0].setFact(kSession.insert(vacs[0]));
 * 
 * vacs[1].setFact(kSession.insert(vacs[1]));
 * 
 * 
 * Lote lote1 = new Lote(20, vacs[0], LocalDate.of(2022, 01, 15));
 * lote1.setFact(kSession.insert(lote1));
 * 
 * Lote lote2 = new Lote(10, vacs[1], LocalDate.of(2022, 01, 15));
 * lote2.setFact(kSession.insert(lote2));
 * 
 * Camara cam_1 = new Camara( "Cariacica", new Localizacao(-20.34900255555123,
 * -40.3901474607013), Arrays.asList(gestor1, gestor2), Arrays.asList(lote1,
 * lote2), 0, 0 ); cam_1.setFact(kSession.insert(cam_1));
 * 
 * 
 * //TODO: ENTRADA DE DADOS POR ARQUIVO (URL) String url =
 * "https://api-temp-umid.herokuapp.com/"; 
 * Thread t1 = new Thread(new TempCamaraWrapper(kSession, cam_1, url)); t1.start();
 * 
 * } catch (Throwable t) { t.printStackTrace(); }
 * 
 * 
 */