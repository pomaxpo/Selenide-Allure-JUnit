package ru.roboforex.core.configs;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class WebDriverConfig {

    public WebDriverConfig(@Value("${selenide.browser}") String browser,
                           @Value("${selenide.customDriverClass:#{null}}") String customDriverClass,
                           @Value("${wdm.driverVersion}") String driverVersion,
                           @Value("${selenide.startMaximized}") Boolean startMaximized,
                           @Value("${selenide.clickViaJS}") Boolean clickViaJS,
                           @Value("${selenide.timeout}") Long timeout,
                           @Value("${extensions:#{null}}") String exstensions) {

        if (customDriverClass != null) {
            Configuration.browser = customDriverClass;
        } else {
            Configuration.browser = browser;
        }

        Configuration.clickViaJs = clickViaJS;
        Configuration.startMaximized = startMaximized;
        Configuration.timeout = timeout;
        Configuration.screenshots = false;

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().version(driverVersion).setup();
                break;
            case "ie":
                WebDriverManager.iedriver().version(driverVersion).arch32().setup();
                break;
            case "chrome":
            default:
                //WebDriverManager.chromedriver().version(driverVersion).setup();
                WebDriverManager.chromedriver().setup();
                break;
        }

        if (exstensions != null) {
            System.setProperty("extensions", exstensions);
        }
    }

}
