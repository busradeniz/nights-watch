package com.nightswatch.dal.entity.violation;

import com.nightswatch.dal.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "VIOLATION_GROUP")
@Access(AccessType.FIELD)
public class ViolationGroup extends AbstractEntity {

    @Column(name = "NAME", unique = true)
    private String name;

    @OneToMany(targetEntity = ViolationProperty.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "violationGroup")
    private Collection<ViolationProperty> violationProperties;

    @OneToMany(targetEntity = Violation.class, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "violationGroup")
    private Collection<Violation> violations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ViolationProperty> getViolationProperties() {
        return violationProperties;
    }

    public void setViolationProperties(Collection<ViolationProperty> violationProperties) {
        this.violationProperties = violationProperties;
    }

    public Collection<Violation> getViolations() {
        return violations;
    }

    public void setViolations(Collection<Violation> violations) {
        this.violations = violations;
    }
}
