package com.nightswatch.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

public final class SpringMvcUtils {

    private static final Logger log = LoggerFactory.getLogger(SpringMvcUtils.class);

    private SpringMvcUtils() {
    }

    public static String getBaseUrl() {
        try {
            log.debug("Base url is generating....");
            final HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            log.debug("HttpServletRequest is read. {}", httpServletRequest);
            final URL url = new URL(httpServletRequest.getRequestURL().toString());
            log.debug("URL is read from servlet request: {}", url);
            final String baseUrl = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + httpServletRequest.getServletContext().getContextPath();
            log.debug("BaseURL({}) is generated", baseUrl);
            return baseUrl;
        } catch (Exception e) {
            log.error("Error while getting base url from servlet request.", e);
            throw new RuntimeException("Requested url cannot be parsed.", e);
        }
    }
}
