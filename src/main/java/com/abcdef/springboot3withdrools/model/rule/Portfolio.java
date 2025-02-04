package com.abcdef.springboot3withdrools.model.rule;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class Portfolio {
    private Long id;
    private Map<String, AssetClassBoundary> strategy;
    private List<Position> positions;
    private int riskRating;
    private boolean riskPassed;
}
