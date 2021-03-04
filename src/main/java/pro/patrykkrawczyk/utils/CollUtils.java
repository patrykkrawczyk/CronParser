package pro.patrykkrawczyk.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollUtils {

    @SafeVarargs
    public static <T> Set<T> initLinkedSet(T... values) {
        return Stream.of(values).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @SafeVarargs
    public static <K, V> Map<K, V> initLinkedMap(Entry<K, V>... values) {
        return Stream.of(values).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }
}
