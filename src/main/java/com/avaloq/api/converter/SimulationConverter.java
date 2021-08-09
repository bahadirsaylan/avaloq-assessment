package com.avaloq.api.converter;

import com.avaloq.api.entity.Simulation;
import com.avaloq.api.model.dto.SimulationDto;

public class SimulationConverter {
    public static SimulationDto toDto(Simulation simulation){
        return SimulationDto.builder()
                .id(simulation.getId())
                .created(simulation.getCreated())
                .modified(simulation.getModified())
                .dices(simulation.getDices())
                .sides(simulation.getSides())
                .rolls(simulation.getRolls())
                .build();
    }

    public Simulation fromDto(SimulationDto simulationDto){
        Simulation simulation = new Simulation();
        simulation.setDices(simulationDto.getDices());
        simulation.setSides(simulationDto.getSides());
        simulation.setRolls(simulationDto.getRolls());
        return simulation;
    }
}
