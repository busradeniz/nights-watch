package com.nightswatch.web.util;

import com.nightswatch.api.dto.violation.DangerLevelTypeDto;
import com.nightswatch.api.dto.violation.FrequencyLevelTypeDto;
import com.nightswatch.api.dto.violation.ViolationStatusTypeDto;
import com.nightswatch.dal.entity.violation.DangerLevelType;
import com.nightswatch.dal.entity.violation.FrequencyLevelType;
import com.nightswatch.dal.entity.violation.ViolationStatusType;

public final class EnumDtoConversionUtils {

    private EnumDtoConversionUtils() {
    }

    public static DangerLevelType convert(DangerLevelTypeDto dangerLevelTypeDto) {
        return DangerLevelType.valueOf(dangerLevelTypeDto.name());
    }

    public static DangerLevelTypeDto convert(DangerLevelType dangerLevelType) {
        return DangerLevelTypeDto.valueOf(dangerLevelType.name());
    }

    public static FrequencyLevelType convert(FrequencyLevelTypeDto frequencyLevelTypeDto) {
        return FrequencyLevelType.valueOf(frequencyLevelTypeDto.name());
    }

    public static FrequencyLevelTypeDto convert(FrequencyLevelType frequencyLevelType) {
        return FrequencyLevelTypeDto.valueOf(frequencyLevelType.name());
    }

    public static ViolationStatusType convert(ViolationStatusTypeDto violationStatusTypeDto) {
        return ViolationStatusType.valueOf(violationStatusTypeDto.name());
    }

    public static ViolationStatusTypeDto convert(ViolationStatusType violationStatusType) {
        return ViolationStatusTypeDto.valueOf(violationStatusType.name());
    }
}
