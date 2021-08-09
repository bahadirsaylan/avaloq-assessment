package com.avaloq.api.controller;

import com.avaloq.api.util.TestData;
import com.avaloq.api.model.request.SimulationRunRequest;
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
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SimulationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SimulationRepository simulationRepository;

    @Autowired
    private SimulationResultRepository simulationResultRepository;


    @Test
    public void shouldReturnCorrectResponseForRunSimulationValidRequest() throws Exception {
        //Arrange
        SimulationRunRequest request = SimulationRunRequest.builder()
                .sides(4)
                .dices(3)
                .rolls(10)
                .build();
        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/simulation")
                        .content(JsonUtil.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dices", is(request.getDices())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sides", is(request.getSides())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rolls", is(request.getRolls())));
    }


    @Test
    public void shouldReturnCorrectStatusCodeAndErrorMessagesForInvalidSimulationRunRequestWithWrongSidesValue() throws Exception{
        //Arrange
        SimulationRunRequest request = SimulationRunRequest.builder()
                .sides(3)
                .dices(3)
                .rolls(10)
                .build();

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/simulation")
                        .content(JsonUtil.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Please check errors")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]", is("sides:The sides of a dice required and should not be less than 4")));
    }

    @Test
    public void shouldReturnCorrectStatusCodeAndErrorMessagesForInvalidSimulationRunRequestWithWrongDicesValue() throws Exception{
        //Arrange
        SimulationRunRequest request = SimulationRunRequest.builder()
                .sides(4)
                .dices(0)
                .rolls(10)
                .build();

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/simulation")
                        .content(JsonUtil.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Please check errors")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]", is("dices:The number of dices value required and must be at least 1")));
    }

    @Test
    public void shouldReturnCorrectStatusCodeAndErrorMessagesForInvalidSimulationRunRequestWithWrongRollsValue() throws Exception{
        //Arrange
        SimulationRunRequest request = SimulationRunRequest.builder()
                .sides(4)
                .dices(2)
                .rolls(0)
                .build();

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/simulation")
                        .content(JsonUtil.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Please check errors")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]", is("rolls:The total number of rolls value required must be at least 1")));
    }


    @Test
    public void shouldReturnCorrectValuesForSimulations() throws Exception {
        //Arrange
        TestData.createSimulation(simulationRepository, 4, 3, 10);
        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/simulation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dices", is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sides", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rolls", is(10)));
    }

    @After
    public void resetDb() {
        simulationRepository.deleteAll();
        simulationResultRepository.deleteAll();
    }
}
