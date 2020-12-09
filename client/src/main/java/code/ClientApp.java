package code;

import code.config.RestServerConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * client.
 *
 * @author wangwenjie
 * @date 2020-12-09
 */
@SpringBootApplication
public class ClientApp {
    public ClientApp(RestTemplate restTemplate, RestServerConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApp.class, args);
    }

    private final RestTemplate restTemplate;
    private final RestServerConfig config;

    @Bean
    public ApplicationRunner init() {
        return args -> {
            String result = this.restTemplate.getForObject(config.getUrl() + "/hello", String.class);
            System.out.println(result);
        };
    }
}
