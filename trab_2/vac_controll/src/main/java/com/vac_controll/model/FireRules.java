package com.vac_controll.model;

import java.util.HashMap;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;

import com.vac_controll.repository.CamaraRepository;
import com.vac_controll.repository.DescarteRepository;
import com.vac_controll.repository.GestorRepository;
import com.vac_controll.repository.LoteRepository;
import com.vac_controll.repository.TempRuimRepository;
import com.vac_controll.repository.VacinaRepository;

public class FireRules implements Runnable {

	@Autowired
	private VacinaRepository vacRepository;

	@Autowired
	private CamaraRepository camRepository;

	@Autowired
	private GestorRepository gestorRepository;

	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	private TempRuimRepository tempRuimRepository;

	@Autowired
	private DescarteRepository descarteRepository;

	public FireRules(VacinaRepository vacRepository, CamaraRepository camRepository, GestorRepository gestorRepository,
			LoteRepository loteRepository, TempRuimRepository tempRuimRepository,
			DescarteRepository descarteRepository) {
		super();
		this.vacRepository = vacRepository;
		this.camRepository = camRepository;
		this.gestorRepository = gestorRepository;
		this.loteRepository = loteRepository;
		this.tempRuimRepository = tempRuimRepository;
		this.descarteRepository = descarteRepository;
	}

	public void run() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-rules");

		HashMap<Long, FactHandle> vacinas_fact = new HashMap<Long, FactHandle>();
		HashMap<Long, FactHandle> camaras_fact = new HashMap<Long, FactHandle>();
		HashMap<Long, FactHandle> gestores_fact = new HashMap<Long, FactHandle>();
		HashMap<Long, FactHandle> lotes_fact = new HashMap<Long, FactHandle>();

		HashMap<Long, FactHandle> temp_ruim_fact = new HashMap<Long, FactHandle>();
		HashMap<Long, FactHandle> descarte_fact = new HashMap<Long, FactHandle>();

		QueryResults results = null;

		while (true) {
			try {
				// ATUALIZANDO WORKMEMORY DE VACINAS
				Iterable<Vacina> vacinas = this.vacRepository.findAll();
				for (Vacina vac : vacinas) {
					FactHandle fact = vacinas_fact.get(vac.getId());
					if (fact != null) {
						kSession.update(fact, vac);
					} else {
						vacinas_fact.put(vac.getId(), kSession.insert(vac));
					}
				}

				// ATUALIZANDO WORKMEMORY DE CAMARAS
				Iterable<Camara> camaras = this.camRepository.findAll();
				for (Camara cam : camaras) {
					FactHandle fact = camaras_fact.get(cam.getId());
					if (fact != null) {
						kSession.update(fact, cam);
					} else {
						camaras_fact.put(cam.getId(), kSession.insert(cam));
					}
				}

				// ATUALIZANDO WORKMEMORY DE GESTORES
				Iterable<Gestor> gestores = this.gestorRepository.findAll();
				for (Gestor gestor : gestores) {
					FactHandle fact = gestores_fact.get(gestor.getId());
					if (fact != null) {
						kSession.update(fact, gestor);
					} else {
						gestores_fact.put(gestor.getId(), kSession.insert(gestor));
					}
				}

				// ATUALIZANDO WORKMEMORY DE LOTES
				Iterable<Lote> lotes = this.loteRepository.findAll();

				for (Lote lote : lotes) {
					FactHandle fact = lotes_fact.get(lote.getId());
					if (fact != null) {
						kSession.update(fact, lote);
					} else {
						lotes_fact.put(lote.getId(), kSession.insert(lote));
					}
				}

				// SINCRONIZA DESCARTES DA WORKMEMORY COM O REPOSITORIO
				results = kSession.getQueryResults("novosDescartes");
				for (QueryResultsRow row : results) {
					Descarte descarte = (Descarte) row.get("descarte");
					Lote lote = descarte.getLote();
					notificarGestores(CodigoAlerta.DESCARTE, lote);

					FactHandle fact = kSession.getFactHandle(descarte);
					descarte = (Descarte) descarteRepository.save(descarte);
					kSession.update(fact, descarte);
					descarte_fact.put(descarte.getId(), fact);

					lote.setUtil(false);
					loteRepository.save(lote);
				}

				// SINCRONIZA TEMPERATURAS RUINS DA WORKMEMORY COM O REPOSITORIO
				results = kSession.getQueryResults("novasTempRuim");
				for (QueryResultsRow row : results) {
					TempRuim tempRuim = (TempRuim) row.get("tempRuim");

					if (tempRuim.getId() == null) {
						FactHandle fact = kSession.getFactHandle(tempRuim);
						tempRuim.setFoiAlterada(false);
						tempRuim = (TempRuim) tempRuimRepository.save(tempRuim);
						kSession.update(fact, tempRuim);
						temp_ruim_fact.put(tempRuim.getId(), fact);

						Lote lote = tempRuim.getLote();
						notificarGestores(tempRuim.getCodigo(), lote);
					} else {
						tempRuim.setFoiAlterada(false);
						tempRuim = (TempRuim) tempRuimRepository.save(tempRuim);
						kSession.update(temp_ruim_fact.get(tempRuim.getId()), tempRuim);
					}

				}

				kSession.fireAllRules();

				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void notificarGestores(CodigoAlerta cod, Lote lote) {
		List<Gestor> gestores = gestorRepository.findByCamaraId(lote.getCamara().getId());
		Gestor gMaisProx = lote.getCamara().getLoc().gestorMaisProx(gestores);

		for (Gestor gestor : gestores) {
			if (gestor == gMaisProx) {
				gestor.enviarMsg(lote, cod, true);
			} else {
				gestor.enviarMsg(lote, cod, false);
			}
		}
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
