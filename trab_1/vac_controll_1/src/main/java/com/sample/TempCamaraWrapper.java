package com.sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;


public class TempCamaraWrapper implements Runnable {
	KieSession kSession;
	Camara cam;
	String url;

	public TempCamaraWrapper(KieSession kSession, Camara cam, String url) {
		super();
		this.kSession = kSession;
		this.cam = cam;
		this.url = url;
	}

	public void run() {
		while(true) {
			try {
				/*

	            HttpURLConnection conexao = (HttpURLConnection) new URL(this.url).openConnection();

	            conexao.setRequestMethod("GET");
	            conexao.setRequestProperty("Accept", "application/json");

	            if (conexao.getResponseCode() != 200) {
	                System.out.println("Erro " + conexao.getResponseCode() + " ao obter dados da URL " + url);
	            }

	            BufferedReader br = new BufferedReader(new InputStreamReader((conexao.getInputStream())));

	            String output = "";
	            String line;
	            
	            while ((line = br.readLine()) != null) {
	                output += line;
	            }
	            
	            //{info: "25.00,42.00,Sexta-11:04:46"}
	            
	            conexao.disconnect();
	            
	            //System.out.println(output);
	            
	            Double temp = Double.parseDouble(output.substring(9, 14));
	            //System.out.println(temp);
	            
	            Double umi = Double.parseDouble(output.substring(15, 20));
	            //System.out.println(umi);
	            
	            int idFinal = output.indexOf('}');
	            String dataSTR = output.substring(21, idFinal-1);
	            //System.out.println(dataSTR);
				
	            */
				Random rand = new Random();
				//Double valor = 18.0 + rand.nextInt(12);
				Double valor = 15.0;
				cam.setTemperatura(valor);
				cam.updatekSession(kSession);

				kSession.fireAllRules();
				
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		
	}

}
