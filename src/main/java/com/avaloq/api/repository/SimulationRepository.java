package com.avaloq.api.repository;

import com.avaloq.api.domain.stats.SimulationStatsByDiceAndSide;
import com.avaloq.api.entity.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SimulationRepository extends JpaRepository<Simulation,Integer> {
    List<Simulation> findByDicesAndSides(int dices, int sides);

    @Query("SELECT new com.avaloq.api.domain.stats.SimulationStatsByDiceAndSide(" +
            "COUNT(s), SUM(s.rolls)) " +
            "FROM Simulation s WHERE s.sides = :sides AND s.dices = :dices GROUP BY s.dices, s.sides")
    List<SimulationStatsByDiceAndSide> findAllGroupByDicesAndSides(
            @Param("dices") int dices,
            @Param("sides") int sides);
}
