package com.nightswatch.web.rest;

import com.nightswatch.api.dto.ResponseType;
import com.nightswatch.api.dto.violation.DeleteUserLikeResponse;
import com.nightswatch.api.dto.violation.UserLikeDto;
import com.nightswatch.api.rest.UserLikeRestService;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.UserLike;
import com.nightswatch.service.user.UserTokenService;
import com.nightswatch.service.violation.UserLikeService;
import com.nightswatch.service.violation.ViolationService;
import com.nightswatch.web.util.UserLikeDtoConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/userLike")
public class UserLikeRestServiceImpl extends AbstractAuthenticatedRestService implements UserLikeRestService {

    private final UserLikeService userLikeService;
    private final ViolationService violationService;

    @Autowired
    public UserLikeRestServiceImpl(final UserTokenService userTokenService,
                                   final UserLikeService userLikeService,
                                   final ViolationService violationService) {
        super(userTokenService);
        this.userLikeService = userLikeService;
        this.violationService = violationService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserLikeDto create(@RequestBody UserLikeDto userLikeDto,
                              @RequestHeader(name = "Authorization", required = false) String token) {
        final User user = this.checkAuthorizationToken(token);
        UserLike userLike = new UserLike();
        userLike.setLikeDate(new Date());
        userLike.setUser(user);
        userLike.setViolation(this.violationService.findOne(userLikeDto.getViolationId()));
        userLike = this.userLikeService.save(userLike);
        return UserLikeDtoConversionUtils.convert(userLike);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public DeleteUserLikeResponse delete(@PathVariable Long id,
                       @RequestHeader(name = "Authorization", required = false) String token) {

        this.checkAuthorizationToken(token);
        this.userLikeService.delete(id);
        DeleteUserLikeResponse deleteUserLikeResponse = new DeleteUserLikeResponse();
        deleteUserLikeResponse.setMessage("Delete like success");
        deleteUserLikeResponse.setResponse(ResponseType.SUCCESS);
        return deleteUserLikeResponse;
    }
}
