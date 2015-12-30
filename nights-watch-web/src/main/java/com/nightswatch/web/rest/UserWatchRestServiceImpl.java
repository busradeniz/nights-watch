package com.nightswatch.web.rest;

import com.nightswatch.api.dto.ResponseType;
import com.nightswatch.api.dto.violation.DeleteEntityResponse;
import com.nightswatch.api.dto.violation.UserWatchDto;
import com.nightswatch.api.rest.UserWatchRestService;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.UserWatch;
import com.nightswatch.service.user.UserTokenService;
import com.nightswatch.service.violation.UserWatchService;
import com.nightswatch.service.violation.ViolationService;
import com.nightswatch.web.util.UserWatchDtoConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userWatch")
public class UserWatchRestServiceImpl extends AbstractAuthenticatedRestService implements UserWatchRestService {

    private final UserWatchService userWatchService;
    private final ViolationService violationService;

    @Autowired
    public UserWatchRestServiceImpl(final UserTokenService userTokenService,
                                    final UserWatchService userWatchService,
                                    final ViolationService violationService) {
        super(userTokenService);
        this.userWatchService = userWatchService;
        this.violationService = violationService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserWatchDto create(@RequestBody UserWatchDto userWatchDto,
                               @RequestHeader(name = "Authorization", required = false) String token) {
        final User user = this.checkAuthorizationToken(token);
        UserWatch userWatch = new UserWatch();
        userWatch.setUser(user);
        userWatch.setViolation(this.violationService.findOne(userWatchDto.getViolationId()));
        userWatch = this.userWatchService.save(userWatch);
        return UserWatchDtoConversionUtils.convert(userWatch);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public DeleteEntityResponse delete(@PathVariable Long id,
                                       @RequestHeader(name = "Authorization", required = false) String token) {
        this.checkAuthorizationToken(token);
        this.userWatchService.delete(id);
        DeleteEntityResponse deleteUserWatchResponse = new DeleteEntityResponse();
        deleteUserWatchResponse.setResponse(ResponseType.SUCCESS);
        deleteUserWatchResponse.setMessage("Delete watch success");
        return deleteUserWatchResponse;
    }
}
