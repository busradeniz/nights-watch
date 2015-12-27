package com.nightswatch.web.util;

import com.nightswatch.api.dto.violation.AbstractViolationDto;
import com.nightswatch.api.dto.violation.CreateViolationRequestDto;
import com.nightswatch.api.dto.violation.ViolationDto;
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
        violation.setCustomProperties(abstractViolationDto.getCustomProperties());
        return violation;
    }

    public static ViolationDto convert(final Violation violation) {
        final ViolationDto violationDto = new ViolationDto();
        violationDto.setId(violation.getId());
        violationDto.setTitle(violation.getTitle());
        violationDto.setViolationDate(violation.getViolationDate());
        violationDto.setDescription(violation.getDescription());
        violationDto.setLatitude(violation.getLatitude());
        violationDto.setLongitude(violation.getLongitude());
        violationDto.setAddress(violation.getAddress());
        violationDto.setViolationStatus(EnumDtoConversionUtils.convert(violation.getViolationStatusType()));
        violationDto.setDangerLevel(EnumDtoConversionUtils.convert(violation.getDangerLevelType()));
        violationDto.setFrequencyLevel(EnumDtoConversionUtils.convert(violation.getFrequencyLevelType()));
        violationDto.setViolationGroupName(violation.getViolationGroup().getName());
        violationDto.setOwner(violation.getOwner().getUsername());
        violationDto.setMedias(MediaDtoConversionUtils.convert(violation.getMedias()));
        violationDto.setTags(TagDtoConversionUtils.convert(violation.getTags()));
        violationDto.setCommentCount(violation.getComments() == null ? 0 : violation.getComments().size());
        violationDto.setUserLikeCount(violation.getUserLikes() == null ? 0 : violation.getUserLikes().size());
        violationDto.setUserWatchCount(violation.getUserWatches() == null ? 0 : violation.getUserWatches().size());
        violationDto.setCustomProperties(violation.getCustomProperties());
        return violationDto;
    }
}
