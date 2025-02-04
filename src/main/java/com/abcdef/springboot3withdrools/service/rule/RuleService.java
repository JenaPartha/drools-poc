package com.abcdef.springboot3withdrools.service.rule;

import com.abcdef.springboot3withdrools.model.rule.Rule;
import com.abcdef.springboot3withdrools.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RuleService {
    @Autowired
    private RuleRepository ruleRepository;

    public Rule saveRule(Rule rule) {
        return ruleRepository.save(rule);
    }

    public List<Rule> getActiveRules() {
        return ruleRepository.findByStatus("ACTIVE");
    }
}
