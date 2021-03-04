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
import static pro.patrykkrawczyk.model.TermType.*;
import static pro.patrykkrawczyk.utils.CollUtils.*;

class CronTabPrinterMixedTermsTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private static Stream<Arguments> print() {
        return Stream.of(
                // Less than 5 default terms
                okCase(
                        initLinkedSet(
                                new Term(MINUTE, initLinkedSet(getIntRange(MINUTE.getMinValue(), MINUTE.getMaxValue()))),
                                new Term(HOUR, initLinkedSet(getIntRange(HOUR.getMinValue(), HOUR.getMaxValue())))
                        ),
                        "minute        " + getIntRangeAsString(MINUTE.getMinValue(), MINUTE.getMaxValue()) + "\n" +
                                "hour          " + getIntRangeAsString(HOUR.getMinValue(), HOUR.getMaxValue()) + "\n" +
                                "command       " + DEFAULT_COMMAND + "\n"
                ),

                // Less terms and they are in different order
                okCase(
                        initLinkedSet(
                                new Term(HOUR, initLinkedSet(getIntRange(HOUR.getMinValue(), HOUR.getMaxValue()))),
                                new Term(MINUTE, initLinkedSet(getIntRange(MINUTE.getMinValue(), MINUTE.getMaxValue())))
                                ),
                                "hour          " + getIntRangeAsString(HOUR.getMinValue(), HOUR.getMaxValue()) + "\n" +
                        "minute        " + getIntRangeAsString(MINUTE.getMinValue(), MINUTE.getMaxValue()) + "\n" +
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
