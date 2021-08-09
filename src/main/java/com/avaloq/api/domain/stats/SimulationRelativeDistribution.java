package com.avaloq.api.domain.stats;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimulationRelativeDistribution {
    private int dicesSum;
    private long totalRolls;
    private float distribution;

    public SimulationRelativeDistribution(int dicesSum, long totalRolls, float distribution){
        this.dicesSum = dicesSum;
        this.totalRolls = totalRolls;
        this.distribution = distribution;
    }

    public SimulationRelativeDistribution(int dicesSum, long totalRolls){
        this.dicesSum = dicesSum;
        this.totalRolls = totalRolls;
    }
}
