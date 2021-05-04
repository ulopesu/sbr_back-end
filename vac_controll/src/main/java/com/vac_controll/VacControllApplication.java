package com.vac_controll;

import com.vac_controll.model.Constante;
import com.vac_controll.model.FireRules;

import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VacControllApplication {
	public static final void main(String[] args) {

		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		Constante.kSession = kContainer.newKieSession("ksession-rules");

		KieBaseConfiguration kbConfig = ks.newKieBaseConfiguration();
		kbConfig.setOption( EventProcessingOption.STREAM );

		SpringApplication.run(VacControllApplication.class, args);
		Thread t1 = new Thread(new FireRules());
		t1.start();
	}
}
