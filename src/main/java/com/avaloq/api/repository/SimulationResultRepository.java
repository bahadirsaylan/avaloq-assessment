package com.avaloq.api.repository;

import com.avaloq.api.domain.stats.SimulationRelativeDistribution;
import com.avaloq.api.entity.SimulationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SimulationResultRepository extends JpaRepository<SimulationResult, Integer> {

    @Query("SELECT new com.avaloq.api.domain.stats.SimulationRelativeDistribution(" +
            "sr.dicesSum, SUM(sr.totalRolls)) " +
            "FROM SimulationResult sr WHERE sr.simulation.id IN ( SELECT s.id FROM Simulation s WHERE s.dices = :dices AND s.sides = :sides) GROUP BY sr.dicesSum")
    List<SimulationRelativeDistribution> findAllGroupByDicesAndSides(
            @Param("dices") int dices,
            @Param("sides") int sides);

    @Query("SELECT new com.avaloq.api.domain.stats.SimulationRelativeDistribution(" +
            "sr.dicesSum, SUM(sr.totalRolls)) " +
            "FROM SimulationResult sr WHERE sr.simulation.id IN ( SELECT s.id FROM Simulation s WHERE s.dices = :dices AND s.sides = :sides) AND sr.dicesSum = :dicesSum GROUP BY sr.dicesSum")
    List<SimulationRelativeDistribution> findAllGroupByDicesAndSidesAndDicesSum(
            @Param("dices") int dices,
            @Param("sides") int sides,
            @Param("dicesSum") int dicesSum);
}
