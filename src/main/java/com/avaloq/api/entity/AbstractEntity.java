package com.avaloq.api.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@EntityListeners(AbstractEntityListener.class)
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AbstractEntity implements Serializable{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private Instant created;
}
