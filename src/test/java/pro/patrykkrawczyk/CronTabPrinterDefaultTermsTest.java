package pro.patrykkrawczyk;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.patrykkrawczyk.model.CronTab;
import pro.patrykkrawczyk.model.Term;
import pro.patrykkrawczyk.service.CronTabPrinter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.patrykkrawczyk.TestUtils.*;
import static pro.patrykkrawczyk.utils.CollUtils.*;
import static pro.patrykkrawczyk.model.TermType.*;

class CronTabPrinterDefaultTermsTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private static Stream<Arguments> print() {
        return Stream.of(
                okCase(
                        initLinkedSet(
                                new Term(MINUTE, initLinkedSet(0, 15, 30, 45)),
                                new Term(HOUR, initLinkedSet(0)),
                                new Term(DAY_OF_MONTH, initLinkedSet(1, 15)),
                                new Term(MONTH, initLinkedSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)),
                                new Term(DAY_OF_WEEK, initLinkedSet(1, 2, 3, 4, 5))
                        ),
                        "minute        0 15 30 45\n" +
                                "hour          0\n" +
                                "day of month  1 15\n" +
                                "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                                "day of week   1 2 3 4 5\n" +
                                "command       " + DEFAULT_COMMAND + "\n"
                ),
                okCase(
                        initLinkedSet(
                                new Term(MINUTE, initLinkedSet(0, 15, 30, 45)),
                                new Term(HOUR, initLinkedSet(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22)),
                                new Term(DAY_OF_MONTH, initLinkedSet(1, 11, 21, 31)),
                                new Term(MONTH, initLinkedSet(1, 6, 11)),
                                new Term(DAY_OF_WEEK, initLinkedSet(0, 1, 2, 3, 4, 5, 6))
                        ),
                        "minute        0 15 30 45\n" +
                                "hour          0 2 4 6 8 10 12 14 16 18 20 22\n" +
                                "day of month  1 11 21 31\n" +
                                "month         1 6 11\n" +
                                "day of week   0 1 2 3 4 5 6\n" +
                                "command       " + DEFAULT_COMMAND + "\n"
                ),
                okCase(
                        initLinkedSet(
                                new Term(MINUTE, initLinkedSet(0)),
                                new Term(HOUR, initLinkedSet(0)),
                                new Term(DAY_OF_MONTH, initLinkedSet(1)),
                                new Term(MONTH, initLinkedSet(1)),
                                new Term(DAY_OF_WEEK, initLinkedSet(0))
                        ),
                        "minute        0\n" +
                                "hour          0\n" +
                                "day of month  1\n" +
                                "month         1\n" +
                                "day of week   0\n" +
                                "command       " + DEFAULT_COMMAND + "\n"
                ),
                okCase(
                        initLinkedSet(
                                new Term(MINUTE, initLinkedSet(0, 1)),
                                new Term(HOUR, initLinkedSet(0, 1)),
                                new Term(DAY_OF_MONTH, initLinkedSet(1, 2)),
                                new Term(MONTH, initLinkedSet(1, 2)),
                                new Term(DAY_OF_WEEK, initLinkedSet(0, 1))
                        ),
                        "minute        0 1\n" +
                                "hour          0 1\n" +
                                "day of month  1 2\n" +
                                "month         1 2\n" +
                                "day of week   0 1\n" +
                                "command       " + DEFAULT_COMMAND + "\n"
                ),
                okCase(
                        initLinkedSet(
                                new Term(MINUTE, initLinkedSet(getIntRange(MINUTE.getMinValue(), MINUTE.getMaxValue()))),
                                new Term(HOUR, initLinkedSet(getIntRange(HOUR.getMinValue(), HOUR.getMaxValue()))),
                                new Term(DAY_OF_MONTH, initLinkedSet(getIntRange(DAY_OF_MONTH.getMinValue(), DAY_OF_MONTH.getMaxValue()))),
                                new Term(MONTH, initLinkedSet(getIntRange(MONTH.getMinValue(), MONTH.getMaxValue()))),
                                new Term(DAY_OF_WEEK, initLinkedSet(getIntRange(DAY_OF_WEEK.getMinValue(), DAY_OF_WEEK.getMaxValue())))
                        ),
                        "minute        " + getIntRangeAsString(MINUTE.getMinValue(), MINUTE.getMaxValue()) + "\n" +
                                "hour          " + getIntRangeAsString(HOUR.getMinValue(), HOUR.getMaxValue()) + "\n" +
                                "day of month  " + getIntRangeAsString(DAY_OF_MONTH.getMinValue(), DAY_OF_MONTH.getMaxValue()) + "\n" +
                                "month         " + getIntRangeAsString(MONTH.getMinValue(), MONTH.getMaxValue()) + "\n" +
                                "day of week   " + getIntRangeAsString(DAY_OF_WEEK.getMinValue(), DAY_OF_WEEK.getMaxValue()) + "\n" +
                                "command       " + DEFAULT_COMMAND + "\n"
                ),
                okCase(
                        initLinkedSet(
                                new Term(MINUTE, initLinkedSet(0, 1, 2)),
                                new Term(HOUR, initLinkedSet(0, 1, 2)),
                                new Term(DAY_OF_MONTH, initLinkedSet(1, 2, 3)),
                                new Term(MONTH, initLinkedSet(1, 2, 3)),
                                new Term(DAY_OF_WEEK, initLinkedSet(0, 1, 2))
                        ),
                        "minute        0 1 2\n" +
                                "hour          0 1 2\n" +
                                "day of month  1 2 3\n" +
                                "month         1 2 3\n" +
                                "day of week   0 1 2\n" +
                                "command       " + DEFAULT_COMMAND + "\n"
                ));
    }

    private static Arguments okCase(Set<Term> expectedTerms, String expected) {
        var cronTab = new CronTab(expectedTerms, DEFAULT_COMMAND);
        return Arguments.of(cronTab, expected);
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @ParameterizedTest
    @MethodSource
    void print(CronTab cronTab, String expected) {
        CronTabPrinter.print(cronTab);
        String result = outputStreamCaptor.toString();
        assertEquals(expected, result);
    }
}
