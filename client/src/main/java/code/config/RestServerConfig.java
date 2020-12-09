package code.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * server config.
 *
 * @author wangwenjie
 * @date 2020-12-09
 */
@ConfigurationProperties(prefix = "rest-server")
public class RestServerConfig {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
