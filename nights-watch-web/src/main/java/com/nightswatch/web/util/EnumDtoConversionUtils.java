package com.nightswatch.web.util;

import com.nightswatch.api.dto.user.GenderTypeDto;
import com.nightswatch.api.dto.violation.*;
import com.nightswatch.dal.entity.user.GenderType;
import com.nightswatch.dal.entity.violation.*;

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

    public static GenderType convert(GenderTypeDto genderTypeDto) {
        return GenderType.valueOf(genderTypeDto.name());
    }

    public static GenderTypeDto convert(GenderType genderType) {
        if (genderType == null) {
            return null;
        }
        return GenderTypeDto.valueOf(genderType.name());
    }

    public static CommentType convert(CommentTypeDto commentTypeDto) {
        if (commentTypeDto == null) {
            return CommentType.BASIC;
        }

        return CommentType.valueOf(commentTypeDto.name());
    }

    public static CommentTypeDto convert(CommentType commentType) {
        return CommentTypeDto.valueOf(commentType.name());
    }

    public static ConstraintTypeDto convert(ConstraintType constraintType) {
        return ConstraintTypeDto.valueOf(constraintType.name());
    }

    public static ConstraintType convert(ConstraintTypeDto constraintTypeDto) {
        return ConstraintType.valueOf(constraintTypeDto.name());
    }
}
