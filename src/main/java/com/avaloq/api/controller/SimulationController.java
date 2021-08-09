package com.avaloq.api.controller;

import com.avaloq.api.model.dto.SimulationDto;
import com.avaloq.api.model.dto.SimulationRunResult;
import com.avaloq.api.model.request.SimulationRunRequest;
import com.avaloq.api.model.response.SimulationRunResponse;
import com.avaloq.api.service.SimulationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Slf4j
public class SimulationController {
    @Autowired
    private Validator validator;

    private final SimulationService simulationService;

    @Autowired
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostMapping("/simulation")
    @ResponseBody
    @Operation(summary = "Requests relative statistics compared to the total rolls for all the simulations.")
    @ApiResponse(
            responseCode = "200",
            description = " For a given dice number–dice side combination, returns the relative distribution, compared to the total rolls, for all the simulations. ",
            content = @Content(schema = @Schema(implementation = SimulationRunResponse.class)))
    @ApiResponse(
            responseCode = "404",
            description = " Returned when no distribution is found for given dice and given sides. ")
    public ResponseEntity<SimulationRunResponse> simulate(
            @Valid @RequestBody SimulationRunRequest simulationRunRequest) {

        SimulationRunResult result = simulationService.simulate(simulationRunRequest);
        SimulationRunResponse response =
                SimulationRunResponse.builder()
                        .dices(simulationRunRequest.getDices())
                        .sides(simulationRunRequest.getSides())
                        .rolls(simulationRunRequest.getRolls())
                        .sums(result.getSums())
                        .id(result.getSimulationId())
                        .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/simulation")
    public ResponseEntity<List<SimulationDto>> getSimulations() {
        return ResponseEntity.ok(simulationService.getSimulations());
    }

}
