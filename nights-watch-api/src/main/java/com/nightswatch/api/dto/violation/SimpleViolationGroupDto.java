package com.nightswatch.api.dto.violation;

import com.nightswatch.api.dto.EntityDto;

public class SimpleViolationGroupDto implements EntityDto {
    protected Long id;
    protected String name;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
