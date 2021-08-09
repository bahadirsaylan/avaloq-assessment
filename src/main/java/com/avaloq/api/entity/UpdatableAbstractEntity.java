package com.avaloq.api.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@EntityListeners({UpdatableAbstractEntityListener.class})
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
abstract class UpdatableAbstractEntity extends AbstractEntity {

    @Column
    private Instant modified;
}