package com.avaloq.api.service;

import com.avaloq.api.domain.stats.SimulationRelativeDistribution;
import com.avaloq.api.domain.stats.SimulationStatsByDiceAndSide;
import com.avaloq.api.exception.APIGenericException;
import com.avaloq.api.model.dto.SimulationDto;
import com.avaloq.api.model.dto.SimulationResultDto;
import com.avaloq.api.model.dto.SimulationRunResult;
import com.avaloq.api.model.request.SimulationRunRequest;

import java.util.List;


public interface SimulationService {

    SimulationRunResult simulate(SimulationRunRequest rollRequest);
    SimulationStatsByDiceAndSide getSimulationStatsByDiceAndSide(int dices, int sides) throws  APIGenericException;
    List<SimulationResultDto> getResults();
    List<SimulationDto> getSimulations();
    List<SimulationRelativeDistribution> getRelativeDistribution(int dices, int sides) throws  APIGenericException;
    SimulationRelativeDistribution getRelativeDistribution(int dices, int sides, int dicesSum) throws  APIGenericException;

}
