package com.nightswatch.web.rest;

import com.nightswatch.api.dto.ResponseType;
import com.nightswatch.api.dto.violation.DeleteEntityResponse;
import com.nightswatch.api.dto.violation.ViolationPropertyDto;
import com.nightswatch.api.rest.ViolationPropertyRestService;
import com.nightswatch.dal.entity.violation.ViolationProperty;
import com.nightswatch.service.user.UserTokenService;
import com.nightswatch.service.violation.ViolationGroupService;
import com.nightswatch.service.violation.ViolationPropertyService;
import com.nightswatch.web.util.EnumDtoConversionUtils;
import com.nightswatch.web.util.ViolationPropertyDtoConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/violationProperty")
@Transactional(propagation = Propagation.REQUIRED)
public class ViolationPropertyRestServiceImpl extends AbstractAuthenticatedRestService implements ViolationPropertyRestService {

    private final ViolationPropertyService violationPropertyService;
    private final ViolationGroupService violationGroupService;

    @Autowired
    public ViolationPropertyRestServiceImpl(final UserTokenService userTokenService,
                                            final ViolationPropertyService violationPropertyService,
                                            final ViolationGroupService violationGroupService) {
        super(userTokenService);
        this.violationPropertyService = violationPropertyService;
        this.violationGroupService = violationGroupService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<ViolationPropertyDto> getAll(@RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final List<ViolationProperty> all = this.violationPropertyService.findAll();
        return ViolationPropertyDtoConversionUtils.convert(all);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ViolationPropertyDto create(@RequestBody ViolationPropertyDto violationPropertyDto,
                                       @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        ViolationProperty violationProperty = ViolationPropertyDtoConversionUtils.convert(violationPropertyDto);
        violationProperty.setViolationGroup(violationGroupService.findOne(violationPropertyDto.getViolationGroupId()));
        violationProperty = this.violationPropertyService.save(violationProperty);
        return ViolationPropertyDtoConversionUtils.convert(violationProperty);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ViolationPropertyDto get(@PathVariable Long id,
                                    @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final ViolationProperty violationProperty = violationPropertyService.findOne(id);
        return ViolationPropertyDtoConversionUtils.convert(violationProperty);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ViolationPropertyDto update(@PathVariable Long id,
                                       @RequestBody ViolationPropertyDto violationPropertyDto,
                                       @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        ViolationProperty violationProperty = violationPropertyService.findOne(id);
        violationProperty.setProperty(violationProperty.getProperty());
        violationProperty.setConstraintType(EnumDtoConversionUtils.convert(violationPropertyDto.getConstraintTypeDto()));
        violationProperty.setConstraintValue(violationProperty.getConstraintValue());
        violationProperty.setDescription(violationProperty.getDescription());
        violationProperty.setViolationGroup(violationGroupService.findOne(violationPropertyDto.getViolationGroupId()));
        violationProperty = this.violationPropertyService.save(violationProperty);
        return ViolationPropertyDtoConversionUtils.convert(violationProperty);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public DeleteEntityResponse delete(@PathVariable Long id,
                                       @RequestHeader(name = "Authorization", required = false) final String token) {
        violationPropertyService.delete(id);
        final DeleteEntityResponse deleteEntityResponse = new DeleteEntityResponse();
        deleteEntityResponse.setResponse(ResponseType.SUCCESS);
        deleteEntityResponse.setMessage("Violation Property is deleted!");
        return deleteEntityResponse;
    }
}