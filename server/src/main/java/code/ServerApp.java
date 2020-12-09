package code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * server.
 *
 * @author wangwenjie
 * @date 2020-12-09
 */
@SpringBootApplication
@RestController
public class ServerApp {
    public static void main(String[] args) {
        SpringApplication.run(ServerApp.class, args);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        String str = null;
        str.toString();
        return "hello";
    }


}
