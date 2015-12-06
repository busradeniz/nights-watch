package com.nightswatch.web.rest;

import com.nightswatch.api.dto.violation.CreateViolationRequestDto;
import com.nightswatch.api.dto.violation.ViolationDto;
import com.nightswatch.api.rest.ViolationRestService;
import com.nightswatch.service.user.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;

public class ViolationRestServiceImpl extends AbstractAuthenticatedRestService implements ViolationRestService {

    @Autowired
    public ViolationRestServiceImpl(UserTokenService userTokenService) {
        super(userTokenService);
    }

    @Override
    public ViolationDto get(Long id) {
        return null;
    }

    @Override
    public ViolationDto create(CreateViolationRequestDto createViolationRequestDto, String token) {
        return null;
    }
}
