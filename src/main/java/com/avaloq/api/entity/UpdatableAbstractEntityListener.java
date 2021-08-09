package com.avaloq.api.entity;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UpdatableAbstractEntityListener extends AbstractEntityListener<UpdatableAbstractEntity> {

    @Override
    public void prePersist(UpdatableAbstractEntity abstractEntity) {
        super.prePersist(abstractEntity);
        abstractEntity.setModified(Instant.now());
    }

    @Override
    public void preUpdate(UpdatableAbstractEntity abstractEntity) {
        super.preUpdate(abstractEntity);
        abstractEntity.setModified(Instant.now());
    }
}