package com.avaloq.api.converter;

import com.avaloq.api.entity.SimulationResult;
import com.avaloq.api.model.dto.SimulationResultDto;

public class SimulationResultConverter {
    public static SimulationResultDto toDTO(SimulationResult simulationResult){
        SimulationResultDto resultDto =  SimulationResultDto.builder()
                .id(simulationResult.getId())
                .created(simulationResult.getCreated())
                .modified(simulationResult.getModified())
                .dicesSum(simulationResult.getDicesSum())
                .totalRolls(simulationResult.getTotalRolls())
                .simulationId(simulationResult.getSimulation().getId())
                .build();
        return resultDto;
    }
}
