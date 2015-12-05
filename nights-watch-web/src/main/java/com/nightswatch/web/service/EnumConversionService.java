package com.nightswatch.web.service;

import com.nightswatch.api.dto.violation.DangerLevelTypeDto;
import com.nightswatch.api.dto.violation.FrequencyLevelTypeDto;
import com.nightswatch.api.dto.violation.ViolationStatusTypeDto;
import com.nightswatch.dal.entity.violation.DangerLevelType;
import com.nightswatch.dal.entity.violation.FrequencyLevelType;
import com.nightswatch.dal.entity.violation.ViolationStatusType;

public interface EnumConversionService {

    DangerLevelType convert(DangerLevelTypeDto dangerLevelTypeDto);

    DangerLevelTypeDto convert(DangerLevelType dangerLevelType);

    FrequencyLevelType convert(FrequencyLevelTypeDto frequencyLevelTypeDto);

    FrequencyLevelTypeDto convert(FrequencyLevelType frequencyLevelType);

    ViolationStatusType convert(ViolationStatusTypeDto violationStatusTypeDto);

    ViolationStatusTypeDto convert(ViolationStatusType violationStatusType);
}
