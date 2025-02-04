package com.abcdef.springboot3withdrools.repository;

import com.abcdef.springboot3withdrools.model.rule.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface RuleRepository extends JpaRepository<Rule, UUID> {
    List<Rule> findByStatus(String status);
}
