package ru.roboforex.core;

import java.lang.reflect.Field;
import java.util.List;

import com.codeborne.selenide.SelenideElement;
import ru.roboforex.core.annotatons.Element;
import ru.roboforex.core.exceptions.CoreException;
import ru.roboforex.core.utils.ReflectionUtil;

public abstract class WebObject {

    private List<Field> webBlocksList;
    private List<Field> webBlockList;

    public abstract String getName();

    /**
     * Возвращает SelenideElement
     *
     * @param elementName - имя элемента в модели
     * @return
     */
    public SelenideElement getElement(String elementName) {
        return getElement(this.getClass(), elementName);
    }

    protected SelenideElement getElement(Class clazz, String elementName) {
        Field field = ReflectionUtil.getDeclaredFieldsWithInheritance(clazz).stream()
                .filter(e -> e.isAnnotationPresent(Element.class))
                .filter(e -> e.getAnnotation(Element.class).name().equals(elementName))
                .findFirst().orElseThrow(() -> new CoreException("Элемент с именем '" + elementName + "' не найден на объекте '" + getName() + "'"));
        field.setAccessible(true);
        try {
            return (SelenideElement) field.get(this);
        } catch (IllegalAccessException ex) {
            throw new CoreException("Не удалось получить доступ к элементу с именем '" + elementName + "'");
        }
    }

}
