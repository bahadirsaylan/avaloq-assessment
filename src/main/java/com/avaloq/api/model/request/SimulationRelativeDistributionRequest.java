package com.avaloq.api.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class SimulationRelativeDistributionRequest {
    @NotNull(message = "Dices value required")
    private Integer dices;
    @NotNull(message = "Sides value required")
    private Integer sides;
}
