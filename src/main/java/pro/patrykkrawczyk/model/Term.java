package pro.patrykkrawczyk.model;

import lombok.Value;

import java.util.Set;

@Value
public class Term {

    TermType termType;
    Set<Integer> values;
}
