package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import ru.roboforex.core.Memory;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.WebDriverRunner.getSelenideDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@Slf4j
public class Hooks {

    private static final String IMAGE_PNG = "image/png";

    private static final String PNG_EXTENSION = ".png";

    @Autowired
    Memory memory;

    @Before
    public void beforeScenario() {

    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            takeScreenshot(scenario.getName());
        }
        getWebDriver().quit();
    }

    private void takeScreenshot(String scenarioName) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String scrName = String.format("%s_%s", scenarioName, currentTime);
        WebDriver driver = getSelenideDriver().getWebDriver();
        if (driver != null) {
            if (driver.getClass() == RemoteWebDriver.class) {
                driver = new Augmenter().augment(driver);
            }
            Allure.addAttachment(scrName, IMAGE_PNG,
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)),
                    PNG_EXTENSION);
        }
    }
}
