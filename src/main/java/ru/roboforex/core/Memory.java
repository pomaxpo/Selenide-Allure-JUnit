package ru.roboforex.core;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import ru.roboforex.core.exceptions.CoreException;

@Slf4j
public class Memory {

    //для сохранения переменных и их значений
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private Map<String, String> memory = new HashMap<>();

    //хранит текущую страницу, для экземпляра сценария
    private String currentPage = "";

    public Memory(String startingUrl) {
        memory.put("startingUrl", startingUrl);
    }

    /**
     * Получает значение по ключу в HashMap текущего сценария
     *
     * @param key - ключ для поиска в HashMap
     * @return value - значение по ключу из HashMap
     */
    public String get(String key) {
        if (!memory.containsKey(key)) {
            throw new CoreException("В памяти нет значения для ключа " + key);
        }

        String value = this.memory.get(key);
        log.debug("Из памяти было извлечено значение {} по ключу {}", value, key);
        return this.memory.get(key);
    }

    /**
     * Проверяет, есть ли значение в памяти
     *
     * @param key - ключ для поиска в HashMap
     * @return value - есть ли значение в памяти
     */
    public Boolean isValueExist(String key) {
        return memory.containsKey(key);
    }

    /**
     * Кладет ключ и значение в HashMap текущего сценария
     *
     * @param key   - ключ, которое помещается в HashMap
     * @param value - значение, которое помещается в HashMap
     */
    public void put(String key, String value) {
        this.memory.put(key, value);
        log.debug("В память была положена пара {} {}", key, value);
    }

    /**
     * Возвращает значение текущей страницы, для текущего сценария
     *
     * @return - значение текущей страницы, для текущего сценария
     */
    public String getCurrentPage() {
        if (this.currentPage.equals("")) {
            throw new CoreException("Текущая страница не установлена");
        }

        return this.currentPage;
    }

    /**
     * Устанавливает значение текущей страницы, для текущего сценария
     *
     * @param pageName - имя страницы
     */
    public void setCurrentPage(String pageName) {
        this.currentPage = pageName;
        log.debug("Текущая страница = {}", pageName);
    }

    /**
     * Возвращает значение true или  false в зависимости от того содержится элемент в памяти или нет.
     *
     * @param key - ключ по которому осуществляется проверка.
     * @return - возвращает true, элемент содержится в памяти. Иначе false.
     */
    public Boolean checkMemory(String key) {
        return memory.containsKey(key);
    }


    /**
     * Кладет ключ и значение в HashMap текущего сценария
     *
     * @param key   - ключ, которое помещается в HashMap
     * @param value - значение, которое помещается в HashMap
     */
    public void put(String key, Object value) {
        this.memory.put(key, (String) value);
        log.debug("В память была положена пара {} {}", key, value);

    }
}
