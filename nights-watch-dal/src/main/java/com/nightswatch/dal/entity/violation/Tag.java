package com.nightswatch.dal.entity.violation;

import com.nightswatch.dal.entity.AbstractEntity;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "TAG")
@Access(AccessType.FIELD)
public class Tag extends AbstractEntity {

    @Column(name = "NAME", unique = true, length = 30)
    private String name;
}
