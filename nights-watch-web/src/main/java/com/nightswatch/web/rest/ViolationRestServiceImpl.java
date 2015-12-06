package com.nightswatch.web.rest;

import com.nightswatch.api.dto.violation.CreateViolationRequestDto;
import com.nightswatch.api.dto.violation.ViolationDto;
import com.nightswatch.api.rest.ViolationRestService;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.Violation;
import com.nightswatch.service.MediaService;
import com.nightswatch.service.user.UserTokenService;
import com.nightswatch.service.violation.TagService;
import com.nightswatch.service.violation.ViolationGroupService;
import com.nightswatch.service.violation.ViolationService;
import com.nightswatch.web.util.ViolationDtoConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/violation")
@Transactional(propagation = Propagation.REQUIRED)
public class ViolationRestServiceImpl extends AbstractAuthenticatedRestService implements ViolationRestService {

    private final ViolationService violationService;
    private final TagService tagService;
    private final ViolationGroupService violationGroupService;
    private final MediaService mediaService;

    @Autowired
    public ViolationRestServiceImpl(final UserTokenService userTokenService,
                                    final ViolationService violationService,
                                    final TagService tagService,
                                    final ViolationGroupService violationGroupService,
                                    final MediaService mediaService) {
        super(userTokenService);
        this.violationService = violationService;
        this.tagService = tagService;
        this.violationGroupService = violationGroupService;
        this.mediaService = mediaService;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ViolationDto get(@PathVariable Long id,
                            @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final Violation violation = violationService.findOne(id);
        return ViolationDtoConversionUtils.convert(violation);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ViolationDto create(@RequestBody final CreateViolationRequestDto createViolationRequestDto,
                               @RequestHeader(name = "Authorization", required = false) final String token) {
        final User user = this.checkAuthorizationToken(token);

        Violation violation = ViolationDtoConversionUtils.convert(createViolationRequestDto, user);
        violation.setViolationGroup(violationGroupService.findByName(createViolationRequestDto.getViolationGroupName()));
        violation.setTags(tagService.findAllOrCreate(createViolationRequestDto.getTags().toArray(new String[createViolationRequestDto.getTags().size()])));
        violation.setMedias(mediaService.findAllByIds(createViolationRequestDto.getMedias()));
        violation = violationService.save(violation);
        return ViolationDtoConversionUtils.convert(violation);
    }
}
