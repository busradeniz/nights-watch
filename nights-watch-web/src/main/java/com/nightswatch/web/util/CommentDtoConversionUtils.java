package com.nightswatch.web.util;

import com.nightswatch.api.dto.violation.CommentDto;
import com.nightswatch.dal.entity.violation.Comment;

public final class CommentDtoConversionUtils {

    private CommentDtoConversionUtils() {
    }

    public static CommentDto convert(Comment comment) {
        final CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setUsername(comment.getOwner().getUsername());
        commentDto.setViolationId(comment.getViolation().getId());
        commentDto.setMediaDtos(MediaDtoConversionUtils.convert(comment.getMedias()));
        return commentDto;
    }
}
