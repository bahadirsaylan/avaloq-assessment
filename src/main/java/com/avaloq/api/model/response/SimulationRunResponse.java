package com.avaloq.api.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.UUID;

@Data
@Builder
public class SimulationRunResponse extends AbstractResponse{
    private UUID id;
    private Integer dices;
    private Integer sides;
    private Integer rolls;
    private HashMap<Integer,Integer> sums;
}
