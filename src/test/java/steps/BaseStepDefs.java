package steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import ru.roboforex.core.utils.Random;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import ru.roboforex.Application;
import ru.roboforex.core.Memory;
import ru.roboforex.core.PageManager;
import ru.roboforex.core.configs.WebDriverConfig;

@ContextConfiguration(
        classes = {Application.class, WebDriverConfig.class},
        loader = SpringBootContextLoader.class)
public class BaseStepDefs {

    @Autowired
    PageManager pageManager;

    @Autowired
    Memory memory;

    @Autowired
    Random random;

    protected void waitPageUrlAppear(String pageName) {
        new WebDriverWait(WebDriverRunner.getWebDriver(), Configuration.timeout / 1000)
                .until(ExpectedConditions.urlContains(pageManager.getCurrentPage().getUrlPart()));
    }

    protected SelenideElement getElement(String elementName) {
        SelenideElement element = pageManager.getCurrentPage().getElement(elementName);
        element.scrollTo();
        return element;
    }
}
