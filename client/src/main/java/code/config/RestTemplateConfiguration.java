package code.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

/**
 * RestTemplateConfig.
 *
 * @author wangwenjie
 * @date 2020-12-09
 */
@Configuration
@EnableConfigurationProperties({RestServerConfig.class})
public class RestTemplateConfiguration {

    private static final ThreadLocal<String> localUrl = new ThreadLocal<>();

    static class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
            if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                localUrl.set(httpRequest.getURI().toString());
            }
            return response;
        }
    }

    static class ErrorHandler extends DefaultResponseErrorHandler {

        @Override
        protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR) {
                try {
                    HttpHeaders headers = response.getHeaders();
                    throw new RuntimeException("=> server exception ,请求地址为：" + localUrl.get());
                } finally {
                    localUrl.remove();
                }
            } else {
                super.handleError(response, statusCode);
            }
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateInterceptor()));
        restTemplate.setErrorHandler(new ErrorHandler());
        return restTemplate;
    }
}
