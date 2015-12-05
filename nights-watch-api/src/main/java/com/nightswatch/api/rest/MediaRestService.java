package com.nightswatch.api.rest;

import com.nightswatch.api.dto.MediaDto;

public interface MediaRestService {

    MediaDto get(Long id, String token);

//    MediaDto uploadImage(Object file, String token);
}
