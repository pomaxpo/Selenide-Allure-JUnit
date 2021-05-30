package ru.roboforex.core.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.roboforex.core.Memory;

@Configuration
public class MemoryConfig {

    @Bean(name = "memory")
    @Scope("cucumber-glue")
    public Memory memory(@Value("${url}") String startingUrl) {
        return new Memory(startingUrl);
    }

}
