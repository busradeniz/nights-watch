package com.nightswatch.web.rest;

import com.nightswatch.api.dto.MediaDto;
import com.nightswatch.api.dto.TagDto;
import com.nightswatch.api.dto.violation.*;
import com.nightswatch.api.rest.ViolationRestService;
import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.*;
import com.nightswatch.service.MediaService;
import com.nightswatch.service.user.UserTokenService;
import com.nightswatch.service.violation.*;
import com.nightswatch.web.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/violation")
@Transactional(propagation = Propagation.REQUIRED)
public class ViolationRestServiceImpl extends AbstractAuthenticatedRestService implements ViolationRestService {

    private final ViolationService violationService;
    private final TagService tagService;
    private final ViolationGroupService violationGroupService;
    private final MediaService mediaService;
    private final CommentService commentService;
    private final UserLikeService userLikeService;
    private final UserWatchService userWatchService;

    // TODO Refactor -- Too many parameters
    @Autowired
    public ViolationRestServiceImpl(final UserTokenService userTokenService,
                                    final ViolationService violationService,
                                    final TagService tagService,
                                    final ViolationGroupService violationGroupService,
                                    final MediaService mediaService,
                                    final CommentService commentService,
                                    final UserLikeService userLikeService,
                                    final UserWatchService userWatchService) {
        super(userTokenService);
        this.violationService = violationService;
        this.tagService = tagService;
        this.violationGroupService = violationGroupService;
        this.mediaService = mediaService;
        this.commentService = commentService;
        this.userLikeService = userLikeService;
        this.userWatchService = userWatchService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<ViolationDto> getAll(@RequestParam("longitude") Double longitude,
                                           @RequestParam("latitude") Double latitude,
                                           @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final List<Violation> violations = this.violationService.findAll();
        final Collection<ViolationDto> violationDtos = new ArrayList<>();
        for (Violation violation : violations) {
            violationDtos.add(ViolationDtoConversionUtils.convert(violation));
        }
        return violationDtos;
    }

    @RequestMapping(path = "/top20/mostLiked", method = RequestMethod.GET)
    public Collection<ViolationDto> getMostLikedViolations(@RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final List<Violation> violations = this.violationService.findAllWithMostLikes();
        final Collection<ViolationDto> violationDtos = new ArrayList<>();
        for (Violation violation : violations) {
            violationDtos.add(ViolationDtoConversionUtils.convert(violation));
        }
        return violationDtos;
    }

    @RequestMapping(path = "/top20/newest", method = RequestMethod.GET)
    public Collection<ViolationDto> getNewestViolations(@RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final List<Violation> violations = this.violationService.findAllNewest();
        final Collection<ViolationDto> violationDtos = new ArrayList<>();
        for (Violation violation : violations) {
            violationDtos.add(ViolationDtoConversionUtils.convert(violation));
        }
        return violationDtos;
    }

    @RequestMapping(path = "/top20/owned", method = RequestMethod.GET)
    public Collection<ViolationDto> getOwnedViolations(@RequestParam(value = "violationStatus", required = true) ViolationStatusTypeDto violationStatusTypeDto,
                                                       @RequestHeader(name = "Authorization", required = false) final String token) {
        final User user = this.checkAuthorizationToken(token);
        final List<Violation> violations = this.violationService.findAllByOwnerAndViolationStatusType(user, EnumDtoConversionUtils.convert(violationStatusTypeDto));
        final Collection<ViolationDto> violationDtos = new ArrayList<>();
        for (Violation violation : violations) {
            violationDtos.add(ViolationDtoConversionUtils.convert(violation));
        }
        return violationDtos;
    }

    @RequestMapping(path = "/top20/watched", method = RequestMethod.GET)
    public Collection<ViolationDto> getWatchedViolations(@RequestParam(value = "violationStatus", required = true) ViolationStatusTypeDto violationStatusTypeDto,
                                                         @RequestHeader(name = "Authorization", required = false) final String token) {
        final User user = this.checkAuthorizationToken(token);
        final List<Violation> violations = this.violationService.findAllWatchedViolations(user, EnumDtoConversionUtils.convert(violationStatusTypeDto));
        final Collection<ViolationDto> violationDtos = new ArrayList<>();
        for (Violation violation : violations) {
            violationDtos.add(ViolationDtoConversionUtils.convert(violation));
        }
        return violationDtos;
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
        final Comment historyComment = this.createHistoryComment(violation, user);
        if (violation.getComments() == null) {
            violation.setComments(new ArrayList<Comment>());
        }
        violation.getComments().add(historyComment);
        violation = violationService.save(violation);
        return ViolationDtoConversionUtils.convert(violation);
    }

    public Comment createHistoryComment(Violation violation, User user) {
        final Comment comment = new Comment();
        comment.setCommentType(CommentType.HISTORY);
        comment.setViolation(violation);
        comment.setOwner(user);
        comment.setContent("User (" + user.getUsername() + ") updated violation.");
        return this.commentService.save(comment);
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

    @RequestMapping(path = "/{id}/comments", method = RequestMethod.GET)
    public Collection<CommentDto> comments(@PathVariable Long id,
                                           @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final List<Comment> comments = commentService.findByViolationId(id);
        final Collection<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(CommentDtoConversionUtils.convert(comment));
        }

        return commentDtos;
    }

    @RequestMapping(path = "/{id}/userLikes", method = RequestMethod.GET)
    public Collection<UserLikeDto> userLikes(@PathVariable Long id,
                                             @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final List<UserLike> userLikes = this.userLikeService.findByViolationId(id);
        final Collection<UserLikeDto> userLikeDtos = new ArrayList<>();
        for (UserLike userLike : userLikes) {
            userLikeDtos.add(UserLikeDtoConversionUtils.convert(userLike));
        }
        return userLikeDtos;
    }

    @RequestMapping(path = "/{id}/userWatches", method = RequestMethod.GET)
    public Collection<UserWatchDto> userWatches(@PathVariable Long id,
                                                @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final List<UserWatch> userWatches = this.userWatchService.findByViolationId(id);
        final Collection<UserWatchDto> userWatchDtos = new ArrayList<>();
        for (UserWatch userWatch : userWatches) {
            userWatchDtos.add(UserWatchDtoConversionUtils.convert(userWatch));
        }
        return userWatchDtos;
    }
}
