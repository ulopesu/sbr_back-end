package com.vac_controll.model;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Constante {
	public static final KieServices ks = KieServices.Factory.get();
	public static final KieContainer kContainer = ks.getKieClasspathContainer();
	public static final KieSession kSession = kContainer.newKieSession("ksession-rules");

	public static final int LimiteExposicao = 10000;
	public static final Long DiaEmMili = Long.valueOf(86400000);
	public static final Long SemanaEmMili = Long.valueOf(604800000);

	public static Long localizacao_ids = Long.valueOf(1);
}
