package com.nightswatch.api.rest;

import com.nightswatch.api.dto.violation.CreateViolationRequestDto;
import com.nightswatch.api.dto.violation.ViolationDto;

public interface ViolationRestService {

    ViolationDto get(final Long id);

    ViolationDto create(final CreateViolationRequestDto createViolationRequestDto, final String token);


    // Create --> CreateViolationRequestDto
    // Update --> UpdateViolationRequestDto
    // Read ve Listeleme islemi GET
    // /1 DELETE
    // /1/addMedia
    // /1/removeMedia
    // /1/addTags
    // /1/removeTags
    // /1/addUserLike
    // /1/removeUserLike
    // /1/addComment
    // /1/removeComment
    // /1/comments
    // /1/userLikes
}
