package com.abcdef.springboot3withdrools.controller;

import com.abcdef.springboot3withdrools.model.rule.Rule;
import com.abcdef.springboot3withdrools.service.rule.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rules")
public class RulesController {
    @Autowired
    private RuleService ruleService;

    @PostMapping
    public Rule saveRule(@RequestBody Rule rule) {
        return ruleService.saveRule(rule);
    }

    @GetMapping("/active")
    public List<Rule> getActiveRules() {
        return ruleService.getActiveRules();
    }
}