package pro.patrykkrawczyk;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import pro.patrykkrawczyk.model.TermType;
import pro.patrykkrawczyk.utils.CollUtils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pro.patrykkrawczyk.model.TermType.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class TestUtils {

    static final String DEFAULT_COMMAND = "/usr/bin/find";

    static final Set<TermType> DEFAULT_TERMS = CollUtils.initLinkedSet(
            MINUTE, HOUR, DAY_OF_MONTH, MONTH, DAY_OF_WEEK
    );

    static Integer[] getIntRange(int start, int end) {
        return IntStream.rangeClosed(start, end).boxed().toArray(Integer[]::new);
    }

    static String getIntRangeAsString(int start, int end) {
        return IntStream.rangeClosed(start, end).mapToObj(Integer::toString).collect(Collectors.joining(" "));
    }
}
