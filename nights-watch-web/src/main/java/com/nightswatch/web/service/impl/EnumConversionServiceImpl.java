package com.nightswatch.web.service.impl;

import com.nightswatch.api.dto.violation.DangerLevelTypeDto;
import com.nightswatch.api.dto.violation.FrequencyLevelTypeDto;
import com.nightswatch.api.dto.violation.ViolationStatusTypeDto;
import com.nightswatch.dal.entity.violation.DangerLevelType;
import com.nightswatch.dal.entity.violation.FrequencyLevelType;
import com.nightswatch.dal.entity.violation.ViolationStatusType;
import com.nightswatch.web.service.EnumConversionService;

public class EnumConversionServiceImpl implements EnumConversionService {
    @Override
    public DangerLevelType convert(DangerLevelTypeDto dangerLevelTypeDto) {
        return DangerLevelType.valueOf(dangerLevelTypeDto.name());
    }

    @Override
    public DangerLevelTypeDto convert(DangerLevelType dangerLevelType) {
        return DangerLevelTypeDto.valueOf(dangerLevelType.name());
    }

    @Override
    public FrequencyLevelType convert(FrequencyLevelTypeDto frequencyLevelTypeDto) {
        return FrequencyLevelType.valueOf(frequencyLevelTypeDto.name());
    }

    @Override
    public FrequencyLevelTypeDto convert(FrequencyLevelType frequencyLevelType) {
        return FrequencyLevelTypeDto.valueOf(frequencyLevelType.name());
    }

    @Override
    public ViolationStatusType convert(ViolationStatusTypeDto violationStatusTypeDto) {
        return ViolationStatusType.valueOf(violationStatusTypeDto.name());
    }

    @Override
    public ViolationStatusTypeDto convert(ViolationStatusType violationStatusType) {
        return ViolationStatusTypeDto.valueOf(violationStatusType.name());
    }
}
