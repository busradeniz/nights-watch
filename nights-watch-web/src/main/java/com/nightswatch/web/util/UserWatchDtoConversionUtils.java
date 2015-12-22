package com.nightswatch.web.util;

import com.nightswatch.api.dto.violation.UserWatchDto;
import com.nightswatch.dal.entity.violation.UserWatch;

public final class UserWatchDtoConversionUtils {

    private UserWatchDtoConversionUtils() {
    }

    public static UserWatchDto convert(UserWatch userWatch) {
        final UserWatchDto userWatchDto = new UserWatchDto();
        userWatchDto.setId(userWatch.getId());
        userWatchDto.setUsername(userWatch.getUser().getUsername());
        userWatchDto.setViolationId(userWatch.getViolation().getId());
        return userWatchDto;
    }
}
