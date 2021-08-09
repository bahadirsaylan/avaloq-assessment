package com.avaloq.api.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.Instant;

@Component
public class AbstractEntityListener<T extends AbstractEntity> {

    @PrePersist
    public void prePersist(T abstractEntity) {
        abstractEntity.setCreated(Instant.now());
    }

    @PostPersist
    public void postPersist(T abstractEntity) {
        //TODO: implement or remove
    }

    @PreRemove
    public void preRemove(T abstractEntity) {
        //TODO: implement or remove
    }

    @PostRemove
    public void postRemove(T abstractEntity) {
        //TODO: implement or remove
    }

    @PreUpdate
    public void preUpdate(T abstractEntity) {
        //TODO: implement or remove
    }

    @PostUpdate
    public void postUpdate(T abstractEntity) {
        //TODO: implement or remove
    }

}