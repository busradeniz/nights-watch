package com.nightswatch.web.rest.handler;

import com.nightswatch.api.dto.ErrorDto;
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

/**
 * Each of our exceptions is extends from AbstractRuntimeException which indicates a generic
 * error on server side
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GenericExceptionHandler extends AbstractExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GenericExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto onException(HttpServletRequest request, Exception e) {

        log.error("Exception occured {}:{}", e.getClass().getCanonicalName(), e.getMessage());
        log.debug(e.getMessage(), e);

        final ErrorDto errorDto = new ErrorDto();
        errorDto.setCause(e.getClass().getCanonicalName());
        errorDto.setPath(request.getRequestURI());
        errorDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        final String message = e.getMessage() != null ? e.getMessage() : e.getClass().getCanonicalName();
        errorDto.setMessage(message);

        return errorDto;

    }
}
