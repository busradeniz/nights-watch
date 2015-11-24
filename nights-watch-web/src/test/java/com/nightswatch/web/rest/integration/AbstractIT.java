package com.nightswatch.web.rest.integration;

import com.nightswatch.web.NightsWatchWebApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@WebIntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NightsWatchWebApplication.class)
public abstract class AbstractIT {

    @Value("${local.server.port}")
    protected String port;

    @Value("${server.context-path:/}")
    protected String contextPath;

    protected String baseUrl;

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://localhost:" + port + contextPath;
    }

    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public RestTemplate getSecureTemplate(final String token) {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new HeaderRequestInterceptor("Authorization", token));
        return restTemplate;
    }

    static class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {
        private final String headerName;
        private final String headerValue;

        public HeaderRequestInterceptor(String headerName, String headerValue) {
            this.headerName = headerName;
            this.headerValue = headerValue;
        }

        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            HttpRequestWrapper wrapper = new HttpRequestWrapper(request);
            wrapper.getHeaders().set(this.headerName, this.headerValue);
            return execution.execute(wrapper, body);
        }
    }
}
