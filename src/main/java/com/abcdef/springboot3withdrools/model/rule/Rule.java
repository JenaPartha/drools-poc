package com.abcdef.springboot3withdrools.model.rule;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class Rule {

    @Id
    @GeneratedValue
    private UUID id;

    private String ruleName;
    @Lob
    private String drlContent;
    private String status;
    private int version;
}
