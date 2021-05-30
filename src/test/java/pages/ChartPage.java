package pages;

import com.codeborne.selenide.SelenideElement;
import ru.roboforex.core.WebPage;
import ru.roboforex.core.annotatons.Element;
import ru.roboforex.core.annotatons.Page;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

@Page(name = "Торговый терминал")
public class ChartPage extends WebPage {

    @Element(name = "График")
    public SelenideElement chartImg = $(".chart-container");

    @Element(name = "Детали аккаунта")
    public SelenideElement accountDetailsModal = $(byXpath("//span[contains(@class, 'modal-window__title')]"));

    @Element(name = "Закрыть")
    public SelenideElement accountDetailsModalClose = $(byXpath("//div[contains(@class, 'demo-account-details__close')]//div"));





}
