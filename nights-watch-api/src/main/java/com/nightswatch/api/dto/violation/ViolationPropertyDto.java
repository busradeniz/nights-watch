package com.nightswatch.api.dto.violation;

import com.nightswatch.api.dto.EntityDto;

public class ViolationPropertyDto implements EntityDto {

    private Long id;
    private String property;
    private ConstraintTypeDto constraintTypeDto;
    private String constraintValue;
    private String description;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public ConstraintTypeDto getConstraintTypeDto() {
        return constraintTypeDto;
    }

    public void setConstraintTypeDto(ConstraintTypeDto constraintTypeDto) {
        this.constraintTypeDto = constraintTypeDto;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViolationPropertyDto that = (ViolationPropertyDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (property != null ? !property.equals(that.property) : that.property != null) return false;
        if (constraintTypeDto != null ? !constraintTypeDto.equals(that.constraintTypeDto) : that.constraintTypeDto != null)
            return false;
        if (constraintValue != null ? !constraintValue.equals(that.constraintValue) : that.constraintValue != null)
            return false;
        return !(description != null ? !description.equals(that.description) : that.description != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (property != null ? property.hashCode() : 0);
        result = 31 * result + (constraintTypeDto != null ? constraintTypeDto.hashCode() : 0);
        result = 31 * result + (constraintValue != null ? constraintValue.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ViolationPropertyDto{" +
                "id=" + id +
                ", property='" + property + '\'' +
                ", constraintTypeDto=" + constraintTypeDto +
                ", constraintValue='" + constraintValue + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
