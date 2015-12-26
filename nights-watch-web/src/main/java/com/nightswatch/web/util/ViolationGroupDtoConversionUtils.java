package com.nightswatch.web.util;

import com.nightswatch.api.dto.violation.ViolationGroupDto;
import com.nightswatch.dal.entity.violation.ViolationGroup;

public final class ViolationGroupDtoConversionUtils {

    private ViolationGroupDtoConversionUtils() {
    }

    public static ViolationGroupDto convert(final ViolationGroup violationGroup) {
        final ViolationGroupDto violationGroupDto = new ViolationGroupDto();
        violationGroupDto.setId(violationGroup.getId());
        violationGroupDto.setName(violationGroup.getName());
        violationGroupDto.setViolationPropertyDtos(ViolationPropertyDtoConversionUtils.convert(violationGroup.getViolationProperties()));
        return violationGroupDto;
    }
}
