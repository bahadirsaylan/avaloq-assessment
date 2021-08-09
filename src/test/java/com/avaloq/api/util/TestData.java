package com.avaloq.api.util;

import com.avaloq.api.entity.Simulation;
import com.avaloq.api.entity.SimulationResult;
import com.avaloq.api.repository.SimulationRepository;
import com.avaloq.api.repository.SimulationResultRepository;

public class TestData {

    public static Simulation createSimulation(SimulationRepository simulationRepository, int sides, int dices, int rolls) {
        Simulation simulation = new Simulation();
        simulation.setRolls(rolls);
        simulation.setSides(sides);
        simulation.setDices(dices);
        return simulationRepository.save(simulation);
    }

    public static void createSimulationResult(SimulationResultRepository simulationResultRepository, int totalRolls, int dicesSum, Simulation simulation) {
        SimulationResult sr = new SimulationResult();
        sr.setTotalRolls(totalRolls);
        sr.setDicesSum(dicesSum);
        sr.setSimulation(simulation);
        simulationResultRepository.save(sr);
    }
}
