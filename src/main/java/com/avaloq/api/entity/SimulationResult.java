package com.avaloq.api.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class SimulationResult extends UpdatableAbstractEntity implements Serializable {

    @Column(name = "dices_sum")
    private int dicesSum;

    @Column(name = "total_rolls")
    private int totalRolls;

    @ManyToOne(fetch = FetchType.LAZY)
    private Simulation simulation;
}