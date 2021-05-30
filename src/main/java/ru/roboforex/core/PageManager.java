package ru.roboforex.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import ru.roboforex.core.annotatons.Page;
import ru.roboforex.core.exceptions.CoreException;
import ru.roboforex.core.exceptions.QAException;
import ru.roboforex.core.utils.ReflectionUtil;

@Slf4j
public class PageManager {

    private List<Class<? extends WebPage>> pages = new ArrayList<>();

    private WebPage currentPage;

    public PageManager(String pagesPackage) {
        initPages(pagesPackage);
    }

    private void initPages(String pagesPackage) {
        Set<Class<?>> classSet = ReflectionUtil.getPageClassesAnnotatedWith(pagesPackage, Page.class);
        classSet.forEach((p) -> {
            pages.add((Class<? extends WebPage>) p);
        });
    }

    /**
     * Возвращает текущую страницу
     *
     * @return объект страницы
     */
    public WebPage getCurrentPage() {
        return currentPage;
    }

    /**
     * Инициализарует объект текущей страницы
     *
     * @param pageName - имя модели страницы
     */
    public void setCurrentPage(String pageName) {
        Class<? extends WebPage> clazz = getPageClass(pageName);
        if (currentPage == null || currentPage.getClass() != clazz) {
            this.currentPage = initPage(clazz);
        }
    }

    private WebPage getPageInstance(String pageName) {
        Class pageClass = getPageClass(pageName);
        if (pageClass == null) {
            throw new CoreException("Страница с именем'" + pageName + "' не найдена");
        }
        return initPage(pageClass);
    }

    private <T extends WebPage> T initPage(Class<T> pageClass) {
        return ReflectionUtil.newInstanceOf(pageClass);
    }

    private Class<? extends WebPage> getPageClass(String pageName) {
        return pages.stream()
                .filter(e -> e.getAnnotation(Page.class).name().equals(pageName))
                .findFirst()
                .orElseThrow(() -> new QAException("Страница с именем " + pageName + " не найдена"));
    }
}
