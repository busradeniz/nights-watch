package com.nightswatch.web.rest.handler;

import com.nightswatch.api.dto.ErrorDto;
import com.nightswatch.service.exception.AuthenticationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationFailedExceptionHandler extends AbstractExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFailedExceptionHandler.class);


    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorDto onException(HttpServletRequest request, AuthenticationFailedException e) {

        log.error("Unauthorized for username: {} and password: {}. Probably user was not found for given parameters",
                e.getUsername(), e.getPassword());
        log.debug(e.getMessage(), e);

        final ErrorDto errorDto = new ErrorDto();
        errorDto.setCause(e.getClass().getCanonicalName());
        errorDto.setPath(request.getRequestURI());
        errorDto.setCode(HttpStatus.UNAUTHORIZED.value());

        final String message = e.getMessage() != null ? e.getMessage() : e.getClass().getCanonicalName();
        errorDto.setMessage(message);

        return errorDto;

    }
}
