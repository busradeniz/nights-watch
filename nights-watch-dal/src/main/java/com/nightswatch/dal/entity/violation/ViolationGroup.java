package com.nightswatch.dal.entity.violation;

import com.nightswatch.dal.entity.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "VIOLATION_GROUP")
@Access(AccessType.FIELD)
public class ViolationGroup extends AbstractEntity {

    @Column(name = "NAME", unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
