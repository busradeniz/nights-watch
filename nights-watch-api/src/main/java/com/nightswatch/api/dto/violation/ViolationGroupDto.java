package com.nightswatch.api.dto.violation;

import java.util.Collection;

public class ViolationGroupDto extends SimpleViolationGroupDto {

    private Collection<ViolationPropertyDto> violationPropertyDtos;

    public Collection<ViolationPropertyDto> getViolationPropertyDtos() {
        return violationPropertyDtos;
    }

    public void setViolationPropertyDtos(Collection<ViolationPropertyDto> violationPropertyDtos) {
        this.violationPropertyDtos = violationPropertyDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViolationGroupDto that = (ViolationGroupDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ViolationGroupDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", violationPropertyDtos=" + violationPropertyDtos +
                '}';
    }
}
