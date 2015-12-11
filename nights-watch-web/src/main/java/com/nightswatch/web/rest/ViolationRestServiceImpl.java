package com.nightswatch.web.rest;

import com.nightswatch.api.dto.MediaDto;
import com.nightswatch.api.dto.TagDto;
import com.nightswatch.api.dto.violation.CreateViolationRequestDto;
import com.nightswatch.api.dto.violation.UpdateViolationRequestDto;
import com.nightswatch.api.dto.violation.ViolationDto;
import com.nightswatch.api.rest.ViolationRestService;
import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.Tag;
import com.nightswatch.dal.entity.violation.Violation;
import com.nightswatch.service.MediaService;
import com.nightswatch.service.user.UserTokenService;
import com.nightswatch.service.violation.TagService;
import com.nightswatch.service.violation.ViolationGroupService;
import com.nightswatch.service.violation.ViolationService;
import com.nightswatch.web.util.EnumDtoConversionUtils;
import com.nightswatch.web.util.ViolationDtoConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ViolationDto update(@PathVariable Long id,
                               @RequestBody final UpdateViolationRequestDto updateViolationRequestDto,
                               @RequestHeader(name = "Authorization", required = false) final String token) {
        final User user = this.checkAuthorizationToken(token);
        Violation violation = this.violationService.findOne(id);
        violation.setAddress(updateViolationRequestDto.getAddress());
        violation.setDescription(updateViolationRequestDto.getDescription());
        violation.setLatitude(updateViolationRequestDto.getLatitude());
        violation.setLongitude(updateViolationRequestDto.getLongitude());
        violation.setTitle(updateViolationRequestDto.getTitle());
        violation.setViolationStatusType(EnumDtoConversionUtils.convert(updateViolationRequestDto.getViolationStatus()));
        violation.setDangerLevelType(EnumDtoConversionUtils.convert(updateViolationRequestDto.getDangerLevel()));
        violation.setFrequencyLevelType(EnumDtoConversionUtils.convert(updateViolationRequestDto.getFrequencyLevel()));
        violation.setLastModifiedBy(user.getUsername());
        violation.setLastModifiedDate(new Date());
        violation.setViolationGroup(violationGroupService.findByName(updateViolationRequestDto.getViolationGroupName()));
        violation = violationService.save(violation);
        return ViolationDtoConversionUtils.convert(violation);
    }

    @RequestMapping(path = "/{id}/addMedia", method = RequestMethod.POST)
    public ViolationDto addMedia(@PathVariable Long id,
                                 @RequestBody MediaDto mediaDto,
                                 @RequestHeader(name = "Authorization", required = false) final String token) {
        final User user = this.checkAuthorizationToken(token);
        Violation violation = this.violationService.findOne(id);
        final Media media = this.mediaService.findOne(mediaDto.getId());
        violation.getMedias().add(media);
        violation = violationService.save(violation);
        return ViolationDtoConversionUtils.convert(violation);
    }

    @RequestMapping(path = "/{id}/removeMedia", method = RequestMethod.POST)
    public ViolationDto removeMedia(@PathVariable Long id,
                                    @RequestBody MediaDto mediaDto,
                                    @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final Media media = this.mediaService.findOne(mediaDto.getId());
        Violation violation = this.violationService.findOne(id);
        violation.getMedias().remove(media);
        violation = violationService.save(violation);
        return ViolationDtoConversionUtils.convert(violation);
    }

    @RequestMapping(path = "/{id}/addTag", method = RequestMethod.POST)
    public ViolationDto addTag(@PathVariable Long id,
                               @RequestBody TagDto tagDto,
                               @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        Violation violation = this.violationService.findOne(id);
        final Tag tag = this.tagService.findOrCreate(tagDto.getTagName());
        violation.getTags().add(tag);
        violationService.save(violation);
        return ViolationDtoConversionUtils.convert(violation);
    }

    @RequestMapping(path = "/{id}/removeTag", method = RequestMethod.POST)
    public ViolationDto removeTag(@PathVariable Long id,
                                  @RequestBody TagDto tagDto,
                                  @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        Violation violation = this.violationService.findOne(id);
        final Tag tag = this.tagService.findOrCreate(tagDto.getTagName());
        violation.getTags().remove(tag);
        violationService.save(violation);
        return ViolationDtoConversionUtils.convert(violation);
    }
}
