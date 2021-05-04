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

/*
 * THREAD ANTIGA
 * 
 * HttpURLConnection conexao = (HttpURLConnection) new
 * URL(this.url).openConnection();
 * 
 * conexao.setRequestMethod("GET"); conexao.setRequestProperty("Accept",
 * "application/json");
 * 
 * if (conexao.getResponseCode() != 200) { System.out.println("Erro " +
 * conexao.getResponseCode() + " ao obter dados da URL " + url); }
 * 
 * BufferedReader br = new BufferedReader(new
 * InputStreamReader((conexao.getInputStream())));
 * 
 * String output = ""; String line;
 * 
 * while ((line = br.readLine()) != null) { output += line; }
 * 
 * //{info: "25.00,42.00,Sexta-11:04:46"}
 * 
 * conexao.disconnect();
 * 
 * //System.out.println(output);
 * 
 * Double temp = Double.parseDouble(output.substring(9, 14));
 * //System.out.println(temp);
 * 
 * Double umi = Double.parseDouble(output.substring(15, 20));
 * //System.out.println(umi);
 * 
 * int idFinal = output.indexOf('}'); String dataSTR = output.substring(21,
 * idFinal-1); //System.out.println(dataSTR);
 * 
 * // Random rand = new Random(); // Double valor = 18.0 + rand.nextInt(12); //
 * Double valor = 15.0; // cam.setTemperatura(valor); //
 * cam.updatekSession(kSession);
 * 
 */
