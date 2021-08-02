package com.avaloq.api.controller;

import com.avaloq.api.model.request.RollRequest;
import com.avaloq.api.model.response.RollResponse;
import com.avaloq.api.service.SimulationService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@Slf4j
public class SimulationController {

    private final SimulationService simulationService;

    @Autowired
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostMapping("/roll")
    public ResponseEntity<RollResponse> roll(@RequestBody RollRequest rollRequest){
        RollResponse response = RollResponse.builder()
                .sums(simulationService.roll(rollRequest))
                .build();

        return ResponseEntity.ok(response);
    }


}
