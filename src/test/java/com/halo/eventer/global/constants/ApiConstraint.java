package com.halo.eventer.global.constants;

import java.util.Map;

import org.springframework.restdocs.constraints.Constraint;

public class ApiConstraint {

    public static Constraint JavaxNotNull() {
        return new Constraint("javax.validation.constraints.NotNull", Map.of());
    }

    public static Constraint JavaxMin() {
        return new Constraint("javax.validation.constraints.Min", Map.of("value", 1));
    }

    public static Constraint JavaxPattern(String regexp) {
        return new Constraint("javax.validation.constraints.Pattern", Map.of("regexp", regexp));
    }
}
