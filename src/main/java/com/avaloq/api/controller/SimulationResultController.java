package com.avaloq.api.controller;

import com.avaloq.api.domain.stats.SimulationRelativeDistribution;
import com.avaloq.api.domain.stats.SimulationStatsByDiceAndSide;
import com.avaloq.api.exception.APIGenericException;
import com.avaloq.api.model.dto.SimulationResultDto;
import com.avaloq.api.model.request.SimulationRelativeDistributionByDiceSumRequest;
import com.avaloq.api.model.request.SimulationRelativeDistributionRequest;
import com.avaloq.api.model.request.SimulationStatsByDiceAndSideRequest;
import com.avaloq.api.model.response.SimulationRelativeDistributionByDicesSumResponse;
import com.avaloq.api.model.response.SimulationRelativeDistributionResponse;
import com.avaloq.api.model.response.SimulationStatsByDiceAndSideResponse;
import com.avaloq.api.service.SimulationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/simulation")
@Slf4j
public class SimulationResultController {

    private final SimulationService simulationService;

    @Autowired
    public SimulationResultController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @GetMapping("/result")
    public ResponseEntity<List<SimulationResultDto>> getResults(){
        return ResponseEntity.ok(simulationService.getResults());
    }

    @GetMapping("/stats")
    public ResponseEntity<SimulationStatsByDiceAndSideResponse> getSimulationStats(
            @RequestParam(name = "dices") int dices,
            @RequestParam(name = "sides") int sides
            ) {
        SimulationStatsByDiceAndSideRequest simulationRollsRequest = SimulationStatsByDiceAndSideRequest.builder()
                .dices(dices)
                .sides(sides)
                .build();
        SimulationStatsByDiceAndSideResponse simulationStatsByDiceAndSideResponse = null;
        try {
            SimulationStatsByDiceAndSide simulationStatsByDiceAndSide =
                    simulationService.getSimulationStatsByDiceAndSide(
                            simulationRollsRequest.getDices(), simulationRollsRequest.getSides());
            simulationStatsByDiceAndSideResponse = SimulationStatsByDiceAndSideResponse.builder()
                    .diceSides(simulationRollsRequest.getSides())
                    .numberOfDices(simulationRollsRequest.getDices())
                    .totalRolls(simulationStatsByDiceAndSide.getTotalRolls())
                    .totalSimulations(simulationStatsByDiceAndSide.getTotalSimulations())
                    .build();
        } catch (APIGenericException e) {
            simulationStatsByDiceAndSideResponse = SimulationStatsByDiceAndSideResponse.builder()
                    .diceSides(simulationRollsRequest.getSides())
                    .numberOfDices(simulationRollsRequest.getDices())
                    .totalRolls(0)
                    .totalSimulations(0)
                    .build();
        }

        return ResponseEntity.ok(simulationStatsByDiceAndSideResponse);
    }

    @GetMapping("/distributions")
    public ResponseEntity<SimulationRelativeDistributionResponse> getSimulationDistribution(
            @RequestParam(name = "dices") int dices,
            @RequestParam(name = "sides") int sides
            ) {
        SimulationRelativeDistributionRequest simulationRelativeDistributionRequest = SimulationRelativeDistributionRequest.builder()
                .dices(dices)
                .sides(sides)
                .build();
        List<SimulationRelativeDistribution> results;
        SimulationRelativeDistributionResponse response;
        try {
            results = simulationService.getRelativeDistribution(
                    simulationRelativeDistributionRequest.getDices(),
                    simulationRelativeDistributionRequest.getSides());
            SimulationStatsByDiceAndSide stats = simulationService.getSimulationStatsByDiceAndSide(
                    simulationRelativeDistributionRequest.getDices(),
                    simulationRelativeDistributionRequest.getSides());

            response = SimulationRelativeDistributionResponse.builder()
                    .dices(simulationRelativeDistributionRequest.getDices())
                    .sides(simulationRelativeDistributionRequest.getSides())
                    .totalRolls(stats.getTotalRolls())
                    .relativeDistribution(results)
                    .build();
        } catch (APIGenericException e) {
            response = SimulationRelativeDistributionResponse.builder()
                    .dices(simulationRelativeDistributionRequest.getDices())
                    .sides(simulationRelativeDistributionRequest.getSides())
                    .relativeDistribution(Collections.emptyList())
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/distribution")
    public ResponseEntity<SimulationRelativeDistributionByDicesSumResponse> getSimulation(
            @RequestParam(name = "dices") int dices,
            @RequestParam(name = "sides") int sides,
            @RequestParam(name = "dicesSum") int dicesSum
            ) {
        SimulationRelativeDistributionByDiceSumRequest simulationRelativeDistributionByDiceSumRequest = SimulationRelativeDistributionByDiceSumRequest.builder()
                .dices(dices)
                .sides(sides)
                .dicesSum(dicesSum)
                .build();
        SimulationRelativeDistribution simulationRelativeDistribution;
        SimulationRelativeDistributionByDicesSumResponse response;
        try {
            simulationRelativeDistribution = simulationService.getRelativeDistribution(
                    simulationRelativeDistributionByDiceSumRequest.getDices(),
                    simulationRelativeDistributionByDiceSumRequest.getSides(),
                    simulationRelativeDistributionByDiceSumRequest.getDicesSum());
            SimulationStatsByDiceAndSide stats = simulationService.getSimulationStatsByDiceAndSide(
                    simulationRelativeDistributionByDiceSumRequest.getDices(),
                    simulationRelativeDistributionByDiceSumRequest.getSides());
            response = SimulationRelativeDistributionByDicesSumResponse.builder()
                    .dices(simulationRelativeDistributionByDiceSumRequest.getDices())
                    .sides(simulationRelativeDistributionByDiceSumRequest.getSides())
                    .dicesSum(simulationRelativeDistributionByDiceSumRequest.getDicesSum())
                    .totalRolls(stats.getTotalRolls())
                    .relativeDistribution(simulationRelativeDistribution)
                    .build();
        } catch (APIGenericException e) {
            response = SimulationRelativeDistributionByDicesSumResponse.builder()
                    .dices(simulationRelativeDistributionByDiceSumRequest.getDices())
                    .sides(simulationRelativeDistributionByDiceSumRequest.getSides())
                    .dicesSum(simulationRelativeDistributionByDiceSumRequest.getDicesSum())
                    .totalRolls(0L)
                    .relativeDistribution(
                            SimulationRelativeDistribution.builder()
                                    .dicesSum(simulationRelativeDistributionByDiceSumRequest.getDicesSum())
                                    .build())
                    .build();
        }
        return ResponseEntity.ok(response);
    }

}
