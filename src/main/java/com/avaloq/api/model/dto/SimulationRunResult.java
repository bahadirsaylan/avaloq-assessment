package com.avaloq.api.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.UUID;

@Builder
@Data
public class SimulationRunResult {
    private UUID simulationId;
    private HashMap<Integer,Integer> sums;
}
