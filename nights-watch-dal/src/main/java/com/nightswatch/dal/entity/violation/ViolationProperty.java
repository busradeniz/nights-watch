package com.nightswatch.dal.entity.violation;

import com.nightswatch.dal.entity.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "VIOLATION_PROPERTY")
@Access(AccessType.FIELD)
public class ViolationProperty extends AbstractEntity {

    @ManyToOne(targetEntity = ViolationGroup.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VIOLATION_GROUP_ID")
    private ViolationGroup violationGroup;

    @Column(name = "PROPERTY")
    private String property;

    @Column(name = "CONSTRAINT_TYPE", length = 6)
    @Enumerated(EnumType.STRING)
    private ConstraintType constraintType;

    @Column(name = "CONSTRAINT_VALUE")
    private String constraintValue;

    @Column(name = "DESCRIPTION")
    private String description;

    public ViolationGroup getViolationGroup() {
        return violationGroup;
    }

    public void setViolationGroup(ViolationGroup violationGroup) {
        this.violationGroup = violationGroup;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(ConstraintType constraintType) {
        this.constraintType = constraintType;
    }

    public String getConstraintValue() {
        return constraintValue;
    }

    public void setConstraintValue(String constraintValue) {
        this.constraintValue = constraintValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
