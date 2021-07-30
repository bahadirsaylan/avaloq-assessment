package com.avaloq.api.entity;

import javax.persistence.*;
import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "simulation")
@Getter @Setter @NoArgsConstructor
public class Simulation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String name;

    public Simulation(String name){
        this.name = name;
    }
}