package pro.patrykkrawczyk.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pro.patrykkrawczyk.model.CronTab;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CronTabPrinter {

    private static final String DEFAULT_LINE_FORMAT = "%-14s%s";

    public static void print(CronTab cronTab) {
        cronTab.getTerms().forEach(t -> {
            var content = t.getValues().stream().map(Object::toString).collect(Collectors.joining(" "));
            printLine(t.getTermType().getName(), content);
        });

        printLine("command", cronTab.getCommand());
    }

    private static void printLine(String label, String content) {
        var line = String.format(DEFAULT_LINE_FORMAT, label, content);
        System.out.println(line);
    }
}
