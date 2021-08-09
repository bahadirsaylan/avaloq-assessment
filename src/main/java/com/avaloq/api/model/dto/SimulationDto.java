package com.avaloq.api.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class SimulationDto {
    private UUID id;
    private Instant created;
    private Instant modified;
    private Integer dices;
    private Integer sides;
    private Integer rolls;
}
