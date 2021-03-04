package pro.patrykkrawczyk.service.term;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pro.patrykkrawczyk.model.TermType;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is a Utility class (static methods) providing mapping implementation for different Terms
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class TermParserHandlers {

    // */15
    static Set<Integer> stepHandler(String term, TermType termType) {
        String stepStr = term.split("/")[1];
        int step = toNumber(stepStr);

        return IntStream.iterate(termType.getMinValue(), n -> n <= termType.getMaxValue(), n -> n + step)
                .boxed()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    // 1,15
    static Set<Integer> commaHandler(String term, TermType termType) {
        return Arrays.stream(term.split(","))
                .map(TermParserHandlers::toNumber)
                .map(n -> validateValueMatchesRange(n, termType))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    // 1-5
    static Set<Integer> rangeHandler(String term, TermType termType) {
        String[] parts = term.split("-");

        Integer leftVal = toNumber(parts[0]);
        validateValueMatchesRange(leftVal, termType);

        Integer rightVal = toNumber(parts[1]);
        validateValueMatchesRange(rightVal, termType);

        if (leftVal > rightVal) {
            String msg = String.format("Term %s should have values in increasing order", term);
            throw new IllegalArgumentException(msg);
        }

        return IntStream.rangeClosed(leftVal, rightVal)
                .boxed()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    // *
    @SuppressWarnings("unused")
    static Set<Integer> asteriskHandler(String term, TermType termType) {
        return IntStream.rangeClosed(termType.getMinValue(), termType.getMaxValue())
                .boxed()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    // 0
    static Set<Integer> singularHandler(String term, TermType termType) {
        Integer value = toNumber(term);
        validateValueMatchesRange(value, termType);
        return Set.of(value);
    }

    static boolean isNumber(String value) {
        try {
            toNumber(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static Integer toNumber(String value) {
        return Integer.parseInt(value);
    }

    private static Integer validateValueMatchesRange(Integer value, TermType termType) {
        boolean belowRange = value < termType.getMinValue();
        boolean aboveRange = value > termType.getMaxValue();

        if (belowRange || aboveRange) {
            String msg = String.format("Value %s is outside range [%s, %s] for term %s", value, termType.getMinValue(), termType.getMaxValue(), termType.getName());
            throw new IllegalArgumentException(msg);
        }

        return value;
    }
}
