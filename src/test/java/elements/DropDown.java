package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.Random;

import static com.codeborne.selenide.Selectors.*;

public class DropDown {

    SelenideElement parent;

    SelenideElement expandButton;

    SelenideElement languageExpandButton;

    ElementsCollection options;

    ElementsCollection languageOptions;


    public DropDown(SelenideElement element) {
        this.parent = element;
        this.expandButton = parent.find(byCssSelector(".visible-ctrl__expand-btn"));
        this.languageExpandButton = parent.find(byXpath("//div[contains(@class, 'selected-item') and contains(text(), 'English')]"));
        this.options = parent.findAll(byClassName("drop-down__item"));
    }

    public void selectFromList(String value) {
        //options.shouldHave(CollectionCondition.texts(value));
        options.filterBy(Condition.value(value)).first().click();
    }

    public void selectFromListById(int id) {
        options.get(id).scrollTo().click();
    }

    public void clickArrow() { expandButton.click(); }

    public void clickLanguage() { languageExpandButton.click(); }

    public void selectRandomFromList() {
        selectFromListById(new Random().nextInt(options.size()));
    }

}
