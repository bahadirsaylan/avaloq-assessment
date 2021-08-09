package com.avaloq.api.controller;

import com.avaloq.api.model.request.SimulationRelativeDistributionByDiceSumRequest;
import com.avaloq.api.util.TestData;
import com.avaloq.api.entity.Simulation;
import com.avaloq.api.model.request.SimulationStatsByDiceAndSideRequest;
import com.avaloq.api.repository.SimulationRepository;
import com.avaloq.api.repository.SimulationResultRepository;
import com.avaloq.api.util.JsonUtil;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SimulationResultControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SimulationRepository simulationRepository;

    @Autowired
    private SimulationResultRepository simulationResultRepository;

    @Test
    public void shouldReturnCorrectResponseForSimulationResults() throws Exception {
        //Arrange
        Simulation simulation = TestData.createSimulation(simulationRepository, 4, 3, 10);
        TestData.createSimulationResult(simulationResultRepository, 10,12, simulation);
        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/simulation/result")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dicesSum", is(12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalRolls", is(10)));
    }

    @Test
    public void shouldReturnExpectedSimulationStatsForValidRequest() throws Exception {
        //Arrange
        Simulation simulation = TestData.createSimulation(simulationRepository,4, 3, 100);
        Simulation simulation2 = TestData.createSimulation(simulationRepository,4, 3, 100);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/simulation/result/stats")
                        .param("dices", "3")
                        .param("sides","4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfDices", is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.diceSides", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalSimulations", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRolls", is(200)));

    }

    @Test
    public void shouldReturnExpectedRelativeDistributionForValidRequest() throws Exception {
        //Arrange
        Simulation simulation = TestData.createSimulation(simulationRepository,4, 3, 100);
        TestData.createSimulationResult(simulationResultRepository,10,12, simulation);
        TestData.createSimulationResult(simulationResultRepository,10,11, simulation);
        TestData.createSimulationResult(simulationResultRepository,10,10, simulation);
        TestData.createSimulationResult(simulationResultRepository,10,9, simulation);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/simulation/result/distribution")
                        .param("dices", "3")
                        .param("sides","4")
                        .param("dicesSum","12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dices", is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sides", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dicesSum", is(12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRolls", is(100)));
    }

    @After
    public void resetDb() {
        simulationRepository.deleteAll();
        simulationResultRepository.deleteAll();
    }
}
