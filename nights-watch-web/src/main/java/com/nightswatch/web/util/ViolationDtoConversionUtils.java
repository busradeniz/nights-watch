package com.nightswatch.web.util;

import com.nightswatch.api.dto.violation.AbstractViolationDto;
import com.nightswatch.api.dto.violation.CreateViolationRequestDto;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.Violation;

import java.util.Date;

public final class ViolationDtoConversionUtils {

    private ViolationDtoConversionUtils() {
    }

    public static Violation convert(final CreateViolationRequestDto createViolationRequestDto, final User owner) {
        final Violation violation = ViolationDtoConversionUtils.convert(createViolationRequestDto);
        violation.setOwner(owner);
        violation.setViolationDate(new Date());
        return violation;
    }

    public static Violation convert(final AbstractViolationDto abstractViolationDto) {
        final Violation violation = new Violation();
        violation.setAddress(abstractViolationDto.getAddress());
        violation.setDescription(abstractViolationDto.getDescription());
        violation.setLatitude(abstractViolationDto.getLatitude());
        violation.setLongitude(abstractViolationDto.getLongitude());
        violation.setTitle(abstractViolationDto.getTitle());
        violation.setViolationStatusType(EnumDtoConversionUtils.convert(abstractViolationDto.getViolationStatus()));
        violation.setDangerLevelType(EnumDtoConversionUtils.convert(abstractViolationDto.getDangerLevel()));
        violation.setFrequencyLevelType(EnumDtoConversionUtils.convert(abstractViolationDto.getFrequencyLevel()));
        return violation;
    }
}
