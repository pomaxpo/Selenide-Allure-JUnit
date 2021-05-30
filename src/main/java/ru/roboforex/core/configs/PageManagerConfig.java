package ru.roboforex.core.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.roboforex.core.PageManager;

@Configuration
@ComponentScan(basePackages = "ru.roboforex.core")
public class PageManagerConfig {

    @Bean(name = "pageManager")
    public PageManager pageManager(@Value("${packages.pages}") String pagesPackage) {
        return new PageManager(pagesPackage);
    }
}
