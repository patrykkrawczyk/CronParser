package pro.patrykkrawczyk;

import pro.patrykkrawczyk.model.TermType;
import pro.patrykkrawczyk.service.CronTabParser;
import pro.patrykkrawczyk.service.CronTabPrinter;
import pro.patrykkrawczyk.utils.CollUtils;

import java.util.Set;

import static pro.patrykkrawczyk.model.TermType.*;

public class Main {

    private static final Set<TermType> DEFAULT_TERMS = CollUtils.initLinkedSet(
            MINUTE, HOUR, DAY_OF_MONTH, MONTH, DAY_OF_WEEK
    );

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments, expecting exactly 1");
            return;
        }

        var rawCron = args[0];

        if (rawCron == null || rawCron.isBlank()) {
            throw new IllegalArgumentException("cron input cannot be null or empty");
        }

        var crontab = CronTabParser.parse(rawCron, DEFAULT_TERMS);
        CronTabPrinter.print(crontab);
    }
}
