package com.nightswatch.web.util;

import com.nightswatch.api.dto.violation.ViolationPropertyDto;
import com.nightswatch.dal.entity.violation.ViolationProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public final class ViolationPropertyDtoConversionUtils {
    private ViolationPropertyDtoConversionUtils() {
    }

    public static ViolationPropertyDto convert(ViolationProperty violationProperty) {
        final ViolationPropertyDto violationPropertyDto = new ViolationPropertyDto();
        violationPropertyDto.setId(violationProperty.getId());
        violationPropertyDto.setProperty(violationProperty.getProperty());
        violationPropertyDto.setConstraintTypeDto(EnumDtoConversionUtils.convert(violationProperty.getConstraintType()));
        violationPropertyDto.setConstraintValue(violationProperty.getConstraintValue());
        violationPropertyDto.setDescription(violationProperty.getDescription());
        return violationPropertyDto;
    }

    public static Collection<ViolationPropertyDto> convert(Collection<ViolationProperty> violationProperties) {
        if(violationProperties == null || violationProperties.isEmpty()){
            return Collections.emptyList();
        }

        final Collection<ViolationPropertyDto> violationPropertyDtos = new ArrayList<>();
        for (ViolationProperty violationProperty : violationProperties) {
            violationPropertyDtos.add(ViolationPropertyDtoConversionUtils.convert(violationProperty));
        }
        return violationPropertyDtos;
    }
}
