package com.avaloq.api.model.request;

import com.avaloq.api.config.SimulationConstants;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
public class SimulationRunRequest {
    @Min(
            value = SimulationConstants.MIN_NUMBER_OF_SIDES,
            message = "The sides of a dice required and should not be less than 4")
    @Max(value = SimulationConstants.MAX_NUMBER_OF_SIDES, message = "Sides max value exceeded")
    private int sides;

    @Min(
            value = SimulationConstants.MIN_NUMBER_OF_DICE,
            message = "The number of dices value required and must be at least 1")
    @Max(
            value = SimulationConstants.MAX_NUMBER_OF_DICE,
            message = "The number of dices max value exceeded")
    private int dices;

    @Min(
            value = SimulationConstants.MIN_NUMBER_OF_ROLLS,
            message = "The total number of rolls value required must be at least 1")
    @Max(
            value = SimulationConstants.MAX_NUMBER_OF_ROLLS,
            message = "The total number of rolls max value exceeded")
    private int rolls;
}
