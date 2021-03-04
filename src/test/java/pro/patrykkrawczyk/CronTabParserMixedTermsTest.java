package pro.patrykkrawczyk;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.patrykkrawczyk.model.CronTab;
import pro.patrykkrawczyk.model.Term;
import pro.patrykkrawczyk.model.TermType;
import pro.patrykkrawczyk.service.CronTabParser;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.patrykkrawczyk.TestUtils.*;
import static pro.patrykkrawczyk.model.TermType.*;
import static pro.patrykkrawczyk.utils.CollUtils.*;

class CronTabParserMixedTermsTest {

    private static Stream<Arguments> parse() {
        return Stream.of(
                // Less than 5 default terms
                okCase("* *", initLinkedSet(
                        new Term(MINUTE, initLinkedSet(getIntRange(MINUTE.getMinValue(), MINUTE.getMaxValue()))),
                        new Term(HOUR, initLinkedSet(getIntRange(HOUR.getMinValue(), HOUR.getMaxValue())))
                ), initLinkedSet(MINUTE, HOUR)),

                // Less terms and they are in different order
                okCase("* *", initLinkedSet(
                        new Term(HOUR, initLinkedSet(getIntRange(HOUR.getMinValue(), HOUR.getMaxValue()))),
                        new Term(MINUTE, initLinkedSet(getIntRange(MINUTE.getMinValue(), MINUTE.getMaxValue())))
                ), initLinkedSet(HOUR, MINUTE))
        );
    }

    private static Arguments okCase(String rawCronTerms, Set<Term> expectedTerms, Set<TermType> supportedTerms) {
        var cronTab = new CronTab(expectedTerms, DEFAULT_COMMAND);
        return Arguments.of(String.format("%s %s", rawCronTerms, DEFAULT_COMMAND), cronTab, supportedTerms);
    }

    @ParameterizedTest
    @MethodSource
    void parse(String input, CronTab expected, Set<TermType> supportedTerms) {
        var result = CronTabParser.parse(input, supportedTerms);
        assertEquals(expected, result);

        Term[] resultTerms = result.getTerms().toArray(Term[]::new);
        Term[] expectedTerms = expected.getTerms().toArray(Term[]::new);

        assertArrayEquals(expectedTerms, resultTerms);
    }
}
