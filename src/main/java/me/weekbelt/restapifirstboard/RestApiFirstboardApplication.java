package me.weekbelt.restapifirstboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class RestApiFirstboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiFirstboardApplication.class, args);
    }

}
