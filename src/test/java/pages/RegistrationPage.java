package pages;

import com.codeborne.selenide.SelenideElement;
import ru.roboforex.core.WebPage;
import ru.roboforex.core.annotatons.Element;
import ru.roboforex.core.annotatons.Page;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

@Page(name = "Регистрация")
public class RegistrationPage extends WebPage {

    @Element(name = "Email")
    public SelenideElement emailInput = $(by("type", "email"));

    @Element(name = "Имя")
    public SelenideElement nameInput = $(byXpath("//input[contains(@placeholder,'Middle name')]"));

    @Element(name = "Фамилия")
    public SelenideElement familyInput = $(byXpath("//input[contains(@placeholder,'Family')]"));

    @Element(name = "Телефон")
    public SelenideElement phoneInput = $(byXpath("//div[contains(@class, 'phone')]//input"));

    @Element(name = "Открыть счет")
    public SelenideElement registrationButton = $(byXpath("//div[contains(@class, 'button button_type_brand')]"));

    @Element(name = "Код страны")
    public SelenideElement countryDropDown = $(byXpath("//div[contains(@class, 'form__country')]//div[@class = 'drop-down']"));

    @Element(name = "Язык")
    public SelenideElement languageDropDown = $(byXpath("//div[contains(@class, 'lang-selector')]//div[@class = 'drop-down']"));

    @Element(name = "Уже 18")
    public SelenideElement atLeast18CheckBox = $(byXpath("//div[contains(@class, 'atleast18')]//input"));

    @Element(name = "Условия использования cookies")
    public SelenideElement termsOfUseCheckBox = $(byXpath("//div[contains(@class, 'atleast18')]//input"));

    @Element(name = "Согласие на доки на Английском")
    public SelenideElement agreeTermsCheckBox = $(byXpath("//div[contains(@class, 'atleast18')]//input"));


}
