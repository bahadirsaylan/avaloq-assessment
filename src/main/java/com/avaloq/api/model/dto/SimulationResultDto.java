package com.avaloq.api.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class SimulationResultDto {
    private UUID id;
    private Instant created;
    private Instant modified;
    private int dicesSum;
    private int totalRolls;
    private UUID simulationId;
}
