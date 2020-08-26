package fr.springboot.refuge.helper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class HelperClass {

    // Fonction pour vérifier la présence d'un paramètre dans une liste. cf public Animal save(....){....}
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
