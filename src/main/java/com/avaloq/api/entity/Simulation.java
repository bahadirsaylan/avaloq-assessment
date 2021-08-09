package com.avaloq.api.entity;

import javax.persistence.*;
import java.io.Serializable;

import lombok.Data;

@Entity
@Data
public class Simulation extends UpdatableAbstractEntity implements Serializable {

    @Column
    private Integer dices;

    @Column
    private Integer sides;

    @Column
    private Integer rolls;

}