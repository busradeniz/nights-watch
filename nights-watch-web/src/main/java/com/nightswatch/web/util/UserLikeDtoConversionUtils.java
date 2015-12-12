package com.nightswatch.web.util;

import com.nightswatch.api.dto.violation.UserLikeDto;
import com.nightswatch.dal.entity.violation.UserLike;

public final class UserLikeDtoConversionUtils {

    private UserLikeDtoConversionUtils() {
    }

    public static UserLikeDto convert(UserLike userLike) {
        final UserLikeDto userLikeDto = new UserLikeDto();
        userLikeDto.setId(userLike.getId());
        userLikeDto.setLikeDate(userLike.getLikeDate());
        userLikeDto.setUsername(userLike.getUser().getUsername());
        userLikeDto.setViolationId(userLike.getViolation().getId());
        return userLikeDto;
    }
}
