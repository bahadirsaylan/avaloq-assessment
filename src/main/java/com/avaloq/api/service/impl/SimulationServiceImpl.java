package com.avaloq.api.service.impl;

import com.avaloq.api.converter.SimulationConverter;
import com.avaloq.api.converter.SimulationResultConverter;
import com.avaloq.api.domain.stats.SimulationRelativeDistribution;
import com.avaloq.api.domain.stats.SimulationStatsByDiceAndSide;
import com.avaloq.api.entity.Simulation;
import com.avaloq.api.entity.SimulationResult;
import com.avaloq.api.exception.APIGenericException;
import com.avaloq.api.model.dto.SimulationDto;
import com.avaloq.api.model.dto.SimulationResultDto;
import com.avaloq.api.model.dto.SimulationRunResult;
import com.avaloq.api.model.request.SimulationRunRequest;
import com.avaloq.api.repository.SimulationRepository;
import com.avaloq.api.repository.SimulationResultRepository;
import com.avaloq.api.service.SimulationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class SimulationServiceImpl implements SimulationService {
    private SimulationRepository simulationRepository;
    private SimulationResultRepository simulationResultRepository;

    @Autowired
    public SimulationServiceImpl(SimulationRepository simulationRepository, SimulationResultRepository simulationResultRepository){
        this.simulationRepository = simulationRepository;
        this.simulationResultRepository = simulationResultRepository;
    }

    public SimulationRunResult simulate(SimulationRunRequest rollRequest){
        Simulation simulation = new Simulation();
        simulation.setDices(rollRequest.getDices());
        simulation.setSides(rollRequest.getSides());
        simulation.setRolls(rollRequest.getRolls());

        Simulation simulationDb = simulationRepository.save(simulation);

        HashMap<Integer, Integer> results = new HashMap<>();
        int currentValue = 0;
        for(int i = rollRequest.getDices(); i <= rollRequest.getSides()*rollRequest.getDices(); i++){
            results.put(i,0);
        }

        for(int i = 0; i < rollRequest.getRolls(); i++){
            int rollSum = 0;
            for(int j=0; j < rollRequest.getDices(); j++){
                rollSum += (int) Math.floor(rollRequest.getSides() * Math.random()) + 1;
            }

            if(results.get(rollSum) == null){
                results.put(rollSum, 1);
            }else{
                currentValue = results.get(rollSum);
                results.put(rollSum, currentValue+1);
            }
        }

        results.forEach( (k,v) ->{
            SimulationResult simulationResult = new SimulationResult();
            simulationResult.setSimulation(simulationDb);
            simulationResult.setDicesSum(k);
            simulationResult.setTotalRolls(v);
            simulationResultRepository.save(simulationResult);
        });

        return SimulationRunResult.builder()
                .simulationId(simulationDb.getId())
                .sums(results)
                .build();
    }

    public SimulationStatsByDiceAndSide getSimulationStatsByDiceAndSide(int dices, int sides) throws APIGenericException {
        List<SimulationStatsByDiceAndSide> simulationStatsByDiceAndSideList= simulationRepository.findAllGroupByDicesAndSides(dices, sides);
        SimulationStatsByDiceAndSide simulationStatsByDiceAndSide = simulationStatsByDiceAndSideList.stream()
                .findAny()
                .orElseThrow(() -> new APIGenericException("Simulations not found",101));
        return simulationStatsByDiceAndSide;
    }

    public List<SimulationResultDto> getResults(){
        List<SimulationResultDto> results = new ArrayList<>();
        simulationResultRepository.findAll().forEach(simulationResult ->{
            results.add(SimulationResultConverter.toDTO(simulationResult));
        });
        return results;
    }

    public List<SimulationDto> getSimulations(){
        List<SimulationDto> simulations = new ArrayList<>();
        simulationRepository.findAll().forEach(simulation -> {
            simulations.add(SimulationConverter.toDto(simulation));
        });
        return simulations;
    }

    public List<SimulationRelativeDistribution> getRelativeDistribution(int dices, int sides) throws  APIGenericException{
        List<SimulationStatsByDiceAndSide> simulationStatsByDiceAndSideList= simulationRepository.findAllGroupByDicesAndSides(dices, sides);
        SimulationStatsByDiceAndSide simulationStatsByDiceAndSide = simulationStatsByDiceAndSideList.stream()
                .findAny()
                .orElseThrow(() -> new APIGenericException("Simulations not found",102));
        List<SimulationRelativeDistribution> results = simulationResultRepository.findAllGroupByDicesAndSides(dices, sides);
        results.forEach(simulationRelativeDistribution -> {
            simulationRelativeDistribution.setDistribution(100.f*simulationRelativeDistribution.getTotalRolls()/simulationStatsByDiceAndSide.getTotalRolls());
        });
        return results;
    }

    public SimulationRelativeDistribution getRelativeDistribution(int dices, int sides, int dicesSum) throws  APIGenericException{
        List<SimulationStatsByDiceAndSide> simulationStatsByDiceAndSideList= simulationRepository.findAllGroupByDicesAndSides(dices, sides);
        SimulationStatsByDiceAndSide simulationStatsByDiceAndSide = simulationStatsByDiceAndSideList.stream()
                .findAny()
                .orElseThrow(() -> new APIGenericException("Simulations not found",102));
        SimulationRelativeDistribution simulationRelativeDistribution = simulationResultRepository
                .findAllGroupByDicesAndSidesAndDicesSum(dices, sides, dicesSum)
                .stream()
                .findFirst().orElseThrow(() -> new APIGenericException("Simulations not found",103));
        simulationRelativeDistribution.setDistribution(100.f*simulationRelativeDistribution.getTotalRolls()/simulationStatsByDiceAndSide.getTotalRolls());

        return simulationRelativeDistribution;
    }

}
