package pro.patrykkrawczyk.service.term;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pro.patrykkrawczyk.model.TermType;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static java.util.Map.entry;
import static pro.patrykkrawczyk.utils.CollUtils.initLinkedMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TermParser {

    private static final Map<Predicate<String>, BiFunction<String, TermType, Set<Integer>>> PARSERS = initLinkedMap(
            entry(s -> s.contains("/"), TermParserHandlers::stepHandler),
            entry(s -> s.contains(","), TermParserHandlers::commaHandler),
            entry(s -> s.contains("-"), TermParserHandlers::rangeHandler),
            entry(s -> s.equals("*"), TermParserHandlers::asteriskHandler),
            entry(TermParserHandlers::isNumber, TermParserHandlers::singularHandler)
    );

    public static Set<Integer> parse(String term, TermType termType) {
        for (var entry : PARSERS.entrySet()) {
            if (entry.getKey().test(term)) {
                return entry.getValue().apply(term, termType);
            }
        }

        throw new IllegalStateException("Term string could not be matched with parser");
    }
}
