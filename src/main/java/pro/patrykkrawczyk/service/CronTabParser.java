package pro.patrykkrawczyk.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import pro.patrykkrawczyk.model.CronTab;
import pro.patrykkrawczyk.model.Term;
import pro.patrykkrawczyk.model.TermType;
import pro.patrykkrawczyk.service.term.TermParser;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CronTabParser {

    private static final Pattern SINGLE_TERM_REGEXP = Pattern.compile("(\\d+,)+\\d+|(([*\\d])+([/-])\\d+)|\\d+|\\*");

    public static CronTab parse(String input, Set<TermType> termTypes) {
        StringBuilder commandBuilder = new StringBuilder();
        Matcher matcher = SINGLE_TERM_REGEXP.matcher(input);

        LinkedHashSet<Term> terms = termTypes.stream().map(t -> {
            if (!matcher.find()) {
                throw new IllegalArgumentException("Cannot construct term value for " + t.getName());
            }

            String termValue = matcher.group();
            matcher.appendReplacement(commandBuilder, "");
            Set<Integer> termValues = TermParser.parse(termValue, t);

            return new Term(t, termValues);
        }).collect(Collectors.toCollection(LinkedHashSet::new));

        matcher.appendTail(commandBuilder);
        String command = commandBuilder.toString().trim();

        return new CronTab(terms, command);
    }
}
