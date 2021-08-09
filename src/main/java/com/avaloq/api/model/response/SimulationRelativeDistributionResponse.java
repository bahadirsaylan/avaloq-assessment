package com.avaloq.api.model.response;

import com.avaloq.api.domain.stats.SimulationRelativeDistribution;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SimulationRelativeDistributionResponse extends AbstractResponse{
    private Integer dices;
    private Integer sides;
    private Long totalRolls;
    private List<SimulationRelativeDistribution> relativeDistribution;
}
