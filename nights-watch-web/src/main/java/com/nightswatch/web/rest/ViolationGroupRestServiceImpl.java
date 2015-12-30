package com.nightswatch.web.rest;

import com.nightswatch.api.dto.ResponseType;
import com.nightswatch.api.dto.violation.DeleteEntityResponse;
import com.nightswatch.api.dto.violation.SimpleViolationGroupDto;
import com.nightswatch.api.dto.violation.ViolationGroupDto;
import com.nightswatch.api.rest.ViolationGroupRestService;
import com.nightswatch.dal.entity.violation.ViolationGroup;
import com.nightswatch.service.exception.DeleteForbiddenException;
import com.nightswatch.service.user.UserTokenService;
import com.nightswatch.service.violation.ViolationGroupService;
import com.nightswatch.web.util.ViolationGroupDtoConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.POST)
    public ViolationGroupDto create(@RequestBody final SimpleViolationGroupDto simpleViolationGroupDto,
                                    @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        ViolationGroup violationGroup = new ViolationGroup();
        violationGroup.setName(simpleViolationGroupDto.getName());
        violationGroup = this.violationGroupService.save(violationGroup);
        return ViolationGroupDtoConversionUtils.convert(violationGroup);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ViolationGroupDto update(@PathVariable final Long id,
                                    @RequestBody final SimpleViolationGroupDto simpleViolationGroupDto,
                                    @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        ViolationGroup violationGroup = this.violationGroupService.findOne(id);
        violationGroup.setName(simpleViolationGroupDto.getName());
        violationGroup = this.violationGroupService.save(violationGroup);
        return ViolationGroupDtoConversionUtils.convert(violationGroup);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ViolationGroupDto get(@PathVariable final Long id,
                                 @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final ViolationGroup violationGroup = this.violationGroupService.findOne(id);
        return ViolationGroupDtoConversionUtils.convert(violationGroup);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public DeleteEntityResponse delete(@PathVariable final Long id,
                                       @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final ViolationGroup violationGroup = this.violationGroupService.findOne(id);

        if (violationGroup.getViolations() != null && !violationGroup.getViolations().isEmpty()) {
            throw new DeleteForbiddenException("ViolationGroup still has some violation. Either delete or update those violation before deleting violationGroup!");
        }

        this.violationGroupService.delete(id);
        final DeleteEntityResponse deleteEntityResponse = new DeleteEntityResponse();
        deleteEntityResponse.setMessage("ViolationGroup is deleted!");
        deleteEntityResponse.setResponse(ResponseType.SUCCESS);

        return deleteEntityResponse;
    }

}
