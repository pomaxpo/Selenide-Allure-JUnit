package steps;

import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import cucumber.api.java.ru.Допустим;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Тогда;
import elements.DropDown;
import org.openqa.selenium.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import ru.roboforex.core.Memory;
import ru.roboforex.core.exceptions.CoreException;
import ru.roboforex.core.exceptions.QAException;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class StepDefs extends BaseStepDefs {

    @Autowired
    Memory memory;


    @Допустим("^(?:пользователь |он )?открывает страницу Webtrader")
    public void openUrl() {
        open("https://webtrader.roboforex.com/");
        $(".modal-window__title").shouldHave(Condition.text("Authorization"));
    }


    @Допустим("^(?:пользователь |он )?заполняет поле \"([^\"]*)\" (?:значением )?\"([^\"]*)\"$")
    public void пользователь_заполняет_поле_значением(String elementName, String value) {
        getElement(elementName).clear();
        getElement(elementName).waitUntil(visible, Configuration.timeout).val(value);
    }


    @И("^(?:пользователь |он )?заполняет поле \"([^\"]*)\" случайным значением и запоминает как \"([^\"]*)\"$")
    public void fillAndSaveUniqueValueOnMemory(String elementName, String keyName) {
        String randomValue = "email" + random.getIntegerWithLength(7) + "@ya.ru";
        String key = keyName;
        getElement(elementName).waitUntil(visible, Configuration.timeout).setValue(randomValue);
        try {
            memory.put(key, randomValue);
        } catch (Exception | ElementShould e) {
            throw new CoreException("Ошибка при запоминании текста элемента по ключу " + key);
        }
    }

    @И("^(?:пользователь |он )?заполняет поле \"([^\"]*)\" случайным буквенным значением$")
    public void fillUniqueValue(String elementName) {
        String randomValue = random.getEnString(8);
        getElement(elementName).waitUntil(visible, Configuration.timeout).setValue(randomValue);
    }

    @И("^(?:пользователь |он )?заполняет поле \"([^\"]*)\" случайным цыфровым значением$")
    public void fillNumUniqueValue(String elementName) {
        String randomValue = "905" + random.getIntegerWithLength(7);
        getElement(elementName).waitUntil(visible, Configuration.timeout).setValue(randomValue);
    }

    @Допустим("^(?:пользователь |он )?выбирает \"([^\"]*)\" из выпадающего списка \"([^\"]*)\"$")
    public void clickInSelectWithTextInDropDown(String value, String elementName) {
        SelenideElement element = getElement(elementName);
        DropDown list = new DropDown(element);
        list.clickArrow();
        list.selectFromList(value);
    }

    @Допустим("^(?:пользователь |он )?выбирает язык \"([^\"]*)\" из выпадающего списка \"([^\"]*)\"$")
    public void clickInSelectLang(String value, String elementName) {
        SelenideElement element = getElement(elementName);
        DropDown list = new DropDown(element);
        list.clickLanguage();
        list.selectFromList(value);
    }


    @И("^(?:пользователь|он) нажимает (?:кнопку|поле|элемент|текст|чекбокс|радиобаттон) \"([^\"]*)\"$")
    public void пользователь_нажимает_кнопку(String elementName) {
        getElement(elementName).waitUntil(enabled, Configuration.timeout).click();
    }


    @Допустим("^(?:пользователь |он )?находится на странице \"([^\"]*)\"")
    public void пользователь_находится_на_странице(String pageName) {
        pageManager.setCurrentPage(pageName);
        waitPageUrlAppear(pageName);
    }


    @Допустим("^(?:пользователь |он )?отмечает чекбокс \"([^\"]*)\"")
    public void пользователь_отмечает_чекбокс(String elementName) {
        getElement(elementName).setSelected(true);
    }

    @Тогда("^отображается (?:элемент|текст|кнопка|статус|радиобаттон|чекбокс|поле) \"([^\"]*)\"$")
    public void отображается_элемент(String elementName) {
        getElement(elementName).shouldBe(Condition.visible);
    }

    @И("^установлено ожидание в \"([^\"]*)\" секунд$")
    public void userWait(String time) throws Throwable {
        Thread.sleep((int) Double.parseDouble(time) * 1000);
    }

    @И("^(?:пользователь |он )?ждет пока элемент \"([^\"]*)\" исчезнет")
    public void ждет_пока_элемент_исчезнет(String elementName) {
        getElement(elementName).waitUntil(Condition.disappear, Configuration.timeout);

    }

    @Тогда("^на странице отображается текст \"(.*)\"$")
    public void textVisibleOnPage(String text) {
        try {
            Selenide.$(new Selectors.ByText(text)).shouldBe(visible);
        } catch (ElementNotFound | QAException ex) {
            throw new QAException("Текст не отображается на странице " + text + "\n" + ex);
        }
    }

    @Тогда("^на странице отсутствуте текст \"([^\"]*)\"$")
    public void textNotVisibleOnPage(String text) {
        try {
            Selenide.$(new Selectors.ByText(text)).shouldNotBe(visible);
        } catch (Exception ex) {
            throw new QAException("Текст отображается на странице " + text + "\n" + ex);
        }
    }

    @И("^атрибут \"([^\"]*)\" элемента \"([^\"]*)\" содержит значение \"([^\"]*)\"$")
    public void checkAttributeHasValue(String attrName, String elementName, String expected) {
        String factual = getElement(elementName)
                .getAttribute(attrName);
        if (factual != null && factual.contains(expected)) {
            return;
        }
        throw new QAException("Атрибут " + attrName + " элемента " + elementName + " не содержит значение " + expected + ".Фактический результат = " + factual);
    }

    @Тогда("^элемент \"([^\"]*)\" кликабелен$")
    public void checkThatElementIsClickable(String elementName) {
        try {
            getElement(elementName).shouldBe(Condition.enabled);
        } catch (ElementShould ex) {
            throw new QAException("Элемент " + elementName + " не кликабелен");
        }
    }

    @И("^(?:пользователь|он) выбирает в выпадающем списке \"([^\"]*)\" текст \"([^\"]*)\"$")
    public void clickInSelectWithText(String elementName, String text) {
        getElement(elementName).selectOptionContainingText(text);
    }

    @И("^пользователь обновляет страницу$")
    public void reloadPage() {
        refresh();

    }
}
