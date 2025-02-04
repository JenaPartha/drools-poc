package com.abcdef.springboot3withdrools.service.drools;

import com.abcdef.springboot3withdrools.model.rule.Rule;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.KieBase;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.List;

@Service
public class DroolsService {
    public KieBase compileRules(List<Rule> rules) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        rules.forEach(rule ->
                kieFileSystem.write(
                        "src/main/resources/rules/" + rule.getId() + ".drl",
                        kieServices.getResources().newReaderResource(new StringReader(rule.getDrlContent()))
                )
        );

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        KieRepository kieRepository = kieServices.getRepository();
        KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());

        return kieContainer.getKieBase();
    }
}
