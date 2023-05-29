package ru.job4j.chat.service;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Optional;

/**
 * Утилитный типизированный класс для обновления моделей
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class DTOService {

    /**
     * Обновляет объект модели и сохраняет в репозитории.
     * Для обновления объекта используется рефлексия.
     * Для сохранения в репозитории используется метод
     * save конкретного репозитория. После обновления в
     * репозитории возвращается обновленный объект модели
     * обернутый в Optional.
     *
     * @param repository репозиторий
     * @param t          объект модели
     * @param <T>        тип объекта модели
     * @return Optional объект модели
     * @throws InvocationTargetException при выбросе исключения при работе с рефлексией
     * @throws IllegalAccessException    при невозможности вызвать метод объекта
     */
    public static <T extends Model> Optional<T> patchModel(CrudRepository<T,
            Integer> repository, T t)
            throws InvocationTargetException, IllegalAccessException {
        var currentOpt = repository.findById(t.getId());
        if (currentOpt.isEmpty()) {
            return Optional.empty();
        }
        var current = currentOpt.get();
        var methods = current.getClass().getDeclaredMethods();
        var namePerMethod = new HashMap<String, Method>();
        for (var method : methods) {
            var name = method.getName();
            if (name.startsWith("get") || name.startsWith("set")) {
                namePerMethod.put(name, method);
            }
        }
        for (var name : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                var getMethod = namePerMethod.get(name);
                var setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    return Optional.empty();
                }
                var newValue = getMethod.invoke(t);
                if (newValue != null) {
                    setMethod.invoke(current, newValue);
                }
            }
        }
        repository.save(t);
        return Optional.of(current);
    }
}
