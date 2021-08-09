package com.avaloq.api.model.response;

import com.avaloq.api.domain.stats.SimulationRelativeDistribution;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SimulationRelativeDistributionByDicesSumResponse extends AbstractResponse{
    private Integer dices;
    private Integer sides;
    private Integer dicesSum;
    private Long totalRolls;
    private SimulationRelativeDistribution relativeDistribution;
}
