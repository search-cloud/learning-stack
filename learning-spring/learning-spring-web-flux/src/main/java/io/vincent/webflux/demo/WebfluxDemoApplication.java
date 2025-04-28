package io.vincent.webflux.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

//@EnableRedisRepositories
//@EnableCaching
@EnableWebFlux
@SpringBootApplication
public class WebfluxDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebfluxDemoApplication.class);
    }

}
