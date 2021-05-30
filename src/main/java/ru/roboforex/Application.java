package ru.roboforex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.roboforex.core.configs.WebDriverConfig;

@SpringBootApplication
@EnableConfigurationProperties({
        WebDriverConfig.class})
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }
}

