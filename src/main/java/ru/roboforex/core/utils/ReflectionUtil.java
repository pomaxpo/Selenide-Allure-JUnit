package ru.roboforex.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import ru.roboforex.core.exceptions.CoreException;

@Slf4j
public class ReflectionUtil {

    /**
     * Возвращает классы в которых присутствует аннотация annotation
     * Поиск производится внутри пакета с Page классами
     *
     * @param annotation - аннотация, по которой осуществлятся поиск
     * @return - Set<Class> список классов, в которых присутствует аннотация annotation
     */
    public static Set<Class<?>> getPageClassesAnnotatedWith(String pagePackage, Class<? extends Annotation> annotation) {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.addUrls(ClasspathHelper.forPackage(pagePackage)); //устанавливает пакет
        log.debug("Пакет описания страниц и блоков = {}", pagePackage);
        Reflections reflection = new Reflections(builder);
        return reflection.getTypesAnnotatedWith(annotation);
    }

    /**
     * Возвращает классы в которых присутствует аннотация annotation
     * Поиск по всему проекту
     *
     * @param annotation - аннотация, по которой осуществлятся поиск
     * @return - Set<Class> список классов, в которых присутствует аннотация annotation
     */
    public static Set<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> annotation) {
        return new Reflections().getTypesAnnotatedWith(annotation);
    }

    public static <T> T newInstanceOf(Class<T> clazz) {
        try {
            return ConstructorUtils.invokeConstructor(clazz);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException ex) {
            throw new CoreException("Не удалось создать эксземпляр класса " + clazz.getName());
        }
    }

    public static <T> T newInstanceOf(Class<T> clazz, Object... args) {
        try {
            return ConstructorUtils.invokeConstructor(clazz, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException ex) {
            throw new CoreException("Не удалось создать эксземпляр класса " + clazz.getName(), ex.getCause());
        }
    }

    /**
     * Вызывает метод получения значения поля field у класса clazz
     *
     * @param field - поле, значение которого нужно получить
     * @param clazz - класс в котором находится элемент
     * @return - значение поля field
     */
    public static Object extractFieldValueViaReflection(Field field, Class clazz) {
        field.setAccessible(true);
        try {
            return field.get(clazz);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }

    /**
     * Получает значение аннотации класса/поля/метода
     *
     * @param clazz/field/method - аннотированная сущность
     * @param annotationClazz - класс аннотации
     * @param parameterName - имя параметра, содержащие нужное значение
     * @return - значение параметра аннотации
     */
    public static Object getAnnotationValue(Class clazz, Class annotationClazz, String parameterName) {
        if (!clazz.isAnnotationPresent(annotationClazz)) {
            throw new CoreException("Класс " + clazz.getName() + " не содержит аннотацию " + annotationClazz);
        }
        Annotation annotation = clazz.getAnnotation(annotationClazz);
        try {
            return annotation.annotationType().getMethod(parameterName).invoke(annotation);
        } catch (ReflectiveOperationException e) {
            throw new CoreException("Метода " + parameterName + " не существует в аннотации " + annotationClazz + "\n" + e);
        }
    }

    public static Object getAnnotationValue(Field field, Class annotationClazz, String parameterName) {
        if (!field.isAnnotationPresent(annotationClazz)) {
            throw new CoreException("Поле " + field.getName() + " не содержит аннотацию " + annotationClazz);
        }
        Annotation annotation = field.getAnnotation(annotationClazz);
        try {
            return annotation.annotationType().getMethod(parameterName).invoke(annotation);
        } catch (ReflectiveOperationException e) {
            throw new CoreException("Метода " + parameterName + " не существует в аннотации " + annotationClazz + "\n" + e);
        }
    }

    public static Object getAnnotationValue(Method method, Class annotationClazz, String parameterName) {
        if (!method.isAnnotationPresent(annotationClazz)) {
            throw new CoreException("Метод " + method.getName() + " не содержит аннотацию " + annotationClazz);
        }
        Annotation annotation = method.getAnnotation(annotationClazz);
        try {
            return annotation.annotationType().getMethod(parameterName).invoke(annotation);
        } catch (ReflectiveOperationException e) {
            throw new CoreException("Метода " + parameterName + " не существует в аннотации " + annotationClazz + "\n" + e);
        }
    }

    /**
     * Изменяет значение аннотации
     *
     * @param annotation - аннотация
     * @param parametersName - имя параметра
     * @param newValue - новое значение параметра
     */
    public static void setAnnotationValue(Annotation annotation, String parametersName, Object newValue) {
        try {
            Object handler = Proxy.getInvocationHandler(annotation);
            Field f = handler.getClass().getDeclaredField("memberValues");
            f.setAccessible(true);
            Map<String, Object> memberValues = (Map<String, Object>) f.get(handler);
            Object oldValue = memberValues.get(parametersName);
            if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
                throw new IllegalStateException();
            }
            memberValues.put(parametersName, newValue);
        } catch (ReflectiveOperationException e) {
            throw new CoreException("Некорректные значения метода! \n" + e);
        }
    }

    public static List<Field> getDeclaredFieldsWithInheritance(Class clazz) {
        List<Field> fields = new ArrayList();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        for (Class supp = clazz.getSuperclass(); supp != Object.class; supp = supp.getSuperclass()) {
            fields.addAll(Arrays.asList(supp.getDeclaredFields()));
        }

        return fields;
    }
}
