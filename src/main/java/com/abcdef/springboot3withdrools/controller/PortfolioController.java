package com.abcdef.springboot3withdrools.controller;
import com.abcdef.springboot3withdrools.listener.AuditingAgendaEventListener;
import com.abcdef.springboot3withdrools.model.order.OrderDiscount;
import com.abcdef.springboot3withdrools.model.rule.Portfolio;
import com.abcdef.springboot3withdrools.model.rule.Position;
import com.abcdef.springboot3withdrools.model.rule.Rule;
import com.abcdef.springboot3withdrools.service.drools.DroolsService;
import com.abcdef.springboot3withdrools.service.rule.RuleService;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/portfolios")
@RequiredArgsConstructor
public class PortfolioController {
    @Autowired
    private DroolsService droolsService;
    @Autowired
    private RuleService ruleService;
    private final AuditingAgendaEventListener listener;


    private final KieContainer kieContainer;

    @PostMapping("/validate")
    public Portfolio validatePortfolio(@RequestBody Portfolio portfolio) {
//        List<Rule> activeRules = ruleService.getActiveRules();
//        KieBase kieBase = droolsService.compileRules(activeRules);

//        DC.put("CORRELATION_ID", UUID.randomUUID().toString().replace("-", "").substring(0, 8));
//        final KieSession kieSession = kieContainer.newKieSession();
//        kieSession.addEventListener(listener);
//        final OrderDiscount orderDiscount = new OrderDiscount();
//        kieSession.setGlobal("orderDiscount", orderDiscount);
//        kieSession.insert(orderRequest);
//        kieSession.fireAllRules();
//        kieSession.dispose();
//        log.info("Final Result:{}", orderDiscount);

        // KieSession kieSession = kieBase.newKieSession();
//        final KieSession kieSession = kieContainer.newKieSession();
//        portfolio.getPositions().forEach(kieSession::insert);
//        portfolio.getStrategy().values().forEach(kieSession::insert);
//
//        kieSession.fireAllRules();
//        kieSession.dispose();
//        final KieSession kieSession = kieContainer.newKieSession();
//        kieSession.addEventListener(listener);
//        final OrderDiscount orderDiscount = new OrderDiscount();
//        kieSession.setGlobal("orderDiscount", orderDiscount);
//        kieSession.insert(orderRequest);
//        kieSession.fireAllRules();
//        kieSession.dispose();
//        log.info("Final Result:{}", orderDiscount);
//        return orderDiscount;
//
        try {
            // Create KieSession
            KieSession kieSession = kieContainer.newKieSession();
            // Insert facts (simple Integer value)
            // Integer testValue = 5;
            // System.out.println("Inserting fact: " + testValue);
            portfolio.getPositions().forEach(kieSession::insert);
            portfolio.getStrategy().values().forEach(kieSession::insert);
            kieSession.addEventListener(listener);
            // Fire all rules
            System.out.println("Firing rules...");
            int firedRules = kieSession.fireAllRules();

            // Print the number of rules fired
            System.out.println(firedRules + " rules fired.");

            // Dispose of the session
            kieSession.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }




         return portfolio;
    }
}
