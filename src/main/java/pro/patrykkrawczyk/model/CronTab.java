package pro.patrykkrawczyk.model;

import lombok.Value;

import java.util.Set;

@Value
public class CronTab {

    Set<Term> terms;
    String command;
}
