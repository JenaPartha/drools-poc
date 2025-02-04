package com.abcdef.springboot3withdrools.model.rule;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {
    private int id;
    private String assetClass;
    private double marketValue;
    private boolean passed;
}
