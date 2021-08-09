package com.avaloq.api.domain.stats;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimulationStatsByDiceAndSide {
    private long totalSimulations;
    private long totalRolls;

    public SimulationStatsByDiceAndSide(long totalSimulations, long totalRolls) {
        this.totalSimulations = totalSimulations;
        this.totalRolls = totalRolls;
    }
}
