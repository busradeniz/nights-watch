package com.nightswatch.web.rest;

import com.nightswatch.api.dto.violation.ViolationGroupDto;
import com.nightswatch.api.rest.ViolationGroupRestService;
import com.nightswatch.dal.entity.violation.ViolationGroup;
import com.nightswatch.service.user.UserTokenService;
import com.nightswatch.service.violation.ViolationGroupService;
import com.nightswatch.web.util.ViolationGroupDtoConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/violationGroup")
@Transactional(propagation = Propagation.REQUIRED)
public class ViolationGroupRestServiceImpl extends AbstractAuthenticatedRestService implements ViolationGroupRestService {

    private final ViolationGroupService violationGroupService;

    @Autowired
    public ViolationGroupRestServiceImpl(final UserTokenService userTokenService,
                                         final ViolationGroupService violationGroupService) {
        super(userTokenService);
        this.violationGroupService = violationGroupService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<ViolationGroupDto> getAll(@RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final List<ViolationGroup> violationGroups = this.violationGroupService.findAll();
        final Collection<ViolationGroupDto> violationGroupDtos = new ArrayList<>();
        for (ViolationGroup violationGroup : violationGroups) {
            violationGroupDtos.add(ViolationGroupDtoConversionUtils.convert(violationGroup));
        }
        return violationGroupDtos;
    }
}
