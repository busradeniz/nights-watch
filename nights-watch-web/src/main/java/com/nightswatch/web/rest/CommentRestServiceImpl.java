package com.nightswatch.web.rest;

import com.nightswatch.api.dto.violation.CommentDto;
import com.nightswatch.api.dto.violation.CreateCommentDto;
import com.nightswatch.api.dto.violation.UpdateCommentDto;
import com.nightswatch.api.rest.CommentRestService;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.Comment;
import com.nightswatch.service.MediaService;
import com.nightswatch.service.user.UserTokenService;
import com.nightswatch.service.violation.CommentService;
import com.nightswatch.service.violation.ViolationService;
import com.nightswatch.web.util.CommentDtoConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/comment")
@Transactional(propagation = Propagation.REQUIRED)
public class CommentRestServiceImpl extends AbstractAuthenticatedRestService implements CommentRestService {

    private final CommentService commentService;
    private final MediaService mediaService;
    private final ViolationService violationService;


    @Autowired
    public CommentRestServiceImpl(final UserTokenService userTokenService,
                                  final CommentService commentService,
                                  final MediaService mediaService,
                                  final ViolationService violationService) {
        super(userTokenService);
        this.commentService = commentService;
        this.mediaService = mediaService;
        this.violationService = violationService;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public CommentDto get(@PathVariable Long id,
                          @RequestHeader(name = "Authorization", required = false) String token) {
        this.checkAuthorizationToken(token);
        final Comment comment = this.commentService.findOne(id);
        return CommentDtoConversionUtils.convert(comment);
    }

    @RequestMapping(method = RequestMethod.POST)
    public CommentDto create(@RequestBody CreateCommentDto createCommentDto,
                             @RequestHeader(name = "Authorization", required = false) String token) {
        final User user = this.checkAuthorizationToken(token);
        Comment comment = new Comment();
        comment.setOwner(user);
        comment.setViolation(this.violationService.findOne(createCommentDto.getViolationId()));
        comment.setMedias(this.mediaService.findAllByIds(createCommentDto.getMediaIds()));
        comment.setContent(createCommentDto.getContent());
        comment.setCommentDate(new Date());
        comment = this.commentService.save(comment);
        return CommentDtoConversionUtils.convert(comment);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public CommentDto update(@PathVariable Long id,
                             @RequestBody UpdateCommentDto updateCommentDto,
                             @RequestHeader(name = "Authorization", required = false) String token) {
        final User user = this.checkAuthorizationToken(token);
        Comment comment = this.commentService.findOne(id);
        if (!comment.getOwner().getUsername().equals(user.getUsername())) {
            throw new RuntimeException("User does not own this comment");
        }

        if (updateCommentDto.getContent() != null && !updateCommentDto.getContent().isEmpty()) {
            comment.setContent(updateCommentDto.getContent());
        }

        if (updateCommentDto.getMediaIds() != null && !updateCommentDto.getMediaIds().isEmpty()) {
            comment.setMedias(this.mediaService.findAllByIds(updateCommentDto.getMediaIds()));
        }
        comment = this.commentService.save(comment);
        return CommentDtoConversionUtils.convert(comment);
    }
}
