package pro.patrykkrawczyk;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.patrykkrawczyk.model.CronTab;
import pro.patrykkrawczyk.model.Term;
import pro.patrykkrawczyk.service.CronTabParser;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.patrykkrawczyk.TestUtils.*;
import static pro.patrykkrawczyk.model.TermType.*;
import static pro.patrykkrawczyk.utils.CollUtils.*;

class CronTabParserDefaultTermsTest {

    private static Stream<Arguments> parse() {
        return Stream.of(
                okCase("*/15 0 1,15 * 1-5", initLinkedSet(
                        new Term(MINUTE, initLinkedSet(0, 15, 30, 45)),
                        new Term(HOUR, initLinkedSet(0)),
                        new Term(DAY_OF_MONTH, initLinkedSet(1, 15)),
                        new Term(MONTH, initLinkedSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)),
                        new Term(DAY_OF_WEEK, initLinkedSet(1, 2, 3, 4, 5))
                )),
                okCase("*/15 */2 */10 */5 */1", initLinkedSet(
                        new Term(MINUTE, initLinkedSet(0, 15, 30, 45)),
                        new Term(HOUR, initLinkedSet(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22)),
                        new Term(DAY_OF_MONTH, initLinkedSet(1, 11, 21, 31)),
                        new Term(MONTH, initLinkedSet(1, 6, 11)),
                        new Term(DAY_OF_WEEK, initLinkedSet(0, 1, 2, 3, 4, 5, 6))
                )),
                okCase("0 0 1 1 0", initLinkedSet(
                        new Term(MINUTE, initLinkedSet(0)),
                        new Term(HOUR, initLinkedSet(0)),
                        new Term(DAY_OF_MONTH, initLinkedSet(1)),
                        new Term(MONTH, initLinkedSet(1)),
                        new Term(DAY_OF_WEEK, initLinkedSet(0))
                )),
                okCase("0,1 0,1 1,2 1,2 0,1", initLinkedSet(
                        new Term(MINUTE, initLinkedSet(0, 1)),
                        new Term(HOUR, initLinkedSet(0, 1)),
                        new Term(DAY_OF_MONTH, initLinkedSet(1, 2)),
                        new Term(MONTH, initLinkedSet(1, 2)),
                        new Term(DAY_OF_WEEK, initLinkedSet(0, 1))
                )),
                okCase("* * * * *", initLinkedSet(
                        new Term(MINUTE, initLinkedSet(getIntRange(MINUTE.getMinValue(), MINUTE.getMaxValue()))),
                        new Term(HOUR, initLinkedSet(getIntRange(HOUR.getMinValue(), HOUR.getMaxValue()))),
                        new Term(DAY_OF_MONTH, initLinkedSet(getIntRange(DAY_OF_MONTH.getMinValue(), DAY_OF_MONTH.getMaxValue()))),
                        new Term(MONTH, initLinkedSet(getIntRange(MONTH.getMinValue(), MONTH.getMaxValue()))),
                        new Term(DAY_OF_WEEK, initLinkedSet(getIntRange(DAY_OF_WEEK.getMinValue(), DAY_OF_WEEK.getMaxValue())))
                )),
                okCase("0-2 0-2 1-3 1-3 0-2", initLinkedSet(
                        new Term(MINUTE, initLinkedSet(0, 1, 2)),
                        new Term(HOUR, initLinkedSet(0, 1, 2)),
                        new Term(DAY_OF_MONTH, initLinkedSet(1, 2, 3)),
                        new Term(MONTH, initLinkedSet(1, 2, 3)),
                        new Term(DAY_OF_WEEK, initLinkedSet(0, 1, 2))
                ))
        );
    }

    private static Arguments okCase(String rawCronTerms, Set<Term> expectedTerms) {
        var cronTab = new CronTab(expectedTerms, DEFAULT_COMMAND);
        return Arguments.of(String.format("%s %s", rawCronTerms, DEFAULT_COMMAND), cronTab);
    }

    @ParameterizedTest
    @MethodSource
    void parse(String input, CronTab expected) {
        var result = CronTabParser.parse(input, TestUtils.DEFAULT_TERMS);
        assertEquals(expected, result);

        Term[] resultTerms = result.getTerms().toArray(Term[]::new);
        Term[] expectedTerms = expected.getTerms().toArray(Term[]::new);

        assertArrayEquals(expectedTerms, resultTerms);
    }
}
