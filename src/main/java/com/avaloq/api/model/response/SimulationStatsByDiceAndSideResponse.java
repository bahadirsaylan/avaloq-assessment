package com.avaloq.api.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimulationStatsByDiceAndSideResponse extends AbstractResponse{
    private Integer numberOfDices;
    private Integer diceSides;
    private long totalSimulations;
    private long totalRolls;

    public SimulationStatsByDiceAndSideResponse(int numberOfDices, int diceSides, long totalSimulations, long totalRolls) {
        this.numberOfDices = numberOfDices;
        this.diceSides = diceSides;
        this.totalSimulations = totalSimulations;
        this.totalRolls = totalRolls;
    }
}
