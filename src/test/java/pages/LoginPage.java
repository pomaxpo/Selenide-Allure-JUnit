package pages;

import com.codeborne.selenide.SelenideElement;
import ru.roboforex.core.WebPage;
import ru.roboforex.core.annotatons.Element;
import ru.roboforex.core.annotatons.Page;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

@Page(name = "Логин")
public class LoginPage extends WebPage {

    @Element(name = "Email")
    public SelenideElement emailInput = $(by("type", "email"));

    @Element(name = "Пароль")
    public SelenideElement passwordInput = $(by("type", "password"));

    @Element(name = "Login")
    public SelenideElement loginButton = $(byXpath("//div[contains(@class, 'button_type_brand')]"));

    @Element(name = "Регистрация")
    public SelenideElement regButton = $(byXpath("//div[contains(@class, 'button_type_quick-demo')]"));

    @Element(name = "Забыли пароль?")
    public SelenideElement forgotPassButton = $(byXpath("//div[contains(@class, 'remind-link__label')]"));

    @Element(name = "Принять куки")
    public SelenideElement сookiesAllow = $(byXpath("//div[@id = 'AllowCookies_Main']//span[text() = 'Allow']"));
}
