package pro.patrykkrawczyk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pro.patrykkrawczyk.service.CronTabParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CronTabParserValidationTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "999 * * * * /usr/bin/find",
            "$ * * * * /usr/bin/find",
            "a * * * * /usr/bin/find",
            "1-999 * * * * /usr/bin/find",
            "5-1 * * * * /usr/bin/find"
    })
    void parse_ExceptionWhenIllegalInput(String input) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CronTabParser.parse(input, TestUtils.DEFAULT_TERMS);
        });
    }
}
