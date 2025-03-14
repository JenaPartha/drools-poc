package com.abcdef.springboot3withdrools.controller;
import com.abcdef.springboot3withdrools.listener.AuditingAgendaEventListener;
import com.abcdef.springboot3withdrools.model.order.OrderDiscount;
import com.abcdef.springboot3withdrools.model.rule.AssetClassBoundary;
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
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    public List<Portfolio> validatePortfolio(@RequestBody List<Portfolio> portfolios) {
        try {


            Portfolio portfolio = new Portfolio();
            portfolio.setRiskRating(5);


            AssetClassBoundary coreEquityBoundary = new AssetClassBoundary("coreequity", 20.0, 40.0);
            AssetClassBoundary realEstateBoundary = new AssetClassBoundary("realestate", 10.0, 15.0);

            Map<String, AssetClassBoundary> strategy = Map.of(
                    "coreequity", coreEquityBoundary,
                    "realestate", realEstateBoundary
            );
            portfolio.setStrategy(strategy);


            List<Position> positions = List.of(
                    new Position(1, "coreequity", 105.0, false),  // Sum = 15 (Fails: 20-40)
                    new Position(2, "coreequity", 10.0, false),  // Sum = 25 (Passes)
                    new Position(3, "realestate", 12.0, false)   // Sum = 12 (Passes)
            );
            portfolio.setPositions(positions);

            KieSession kieSession = kieContainer.newKieSession();


            portfolio.getStrategy().values().forEach(kieSession::insert);


            portfolio.getPositions().forEach(kieSession::insert);


            int fired = kieSession.fireAllRules();
            System.out.println(fired + " rules fired.");

            kieSession.dispose();


        } catch (Exception e) {
            e.printStackTrace();
        }


       return null;
    }


    @PostMapping("/validateJson")
    public List<Portfolio> validatePortfolioJson(@RequestBody List<Portfolio> portfolios) {
        try {
            KieSession kieSession = kieContainer.newKieSession();

            portfolios.forEach(portfolio -> {
                // Insert AssetClassBoundaries and Positions
                portfolio.getStrategy().values().forEach(kieSession::insert);
                portfolio.getPositions().forEach(kieSession::insert);
            });

            // Fire rules
            int fired = kieSession.fireAllRules();
            System.out.println(fired + " rules fired.");

            kieSession.dispose();
            return portfolios; // Return updated portfolios
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
