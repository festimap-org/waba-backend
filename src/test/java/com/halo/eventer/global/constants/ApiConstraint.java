package com.halo.eventer.global.constants;

import java.util.Map;

import org.springframework.restdocs.constraints.Constraint;

public class ApiConstraint {

    public static Constraint JavaxNotNull() {
        return new Constraint("javax.validation.constraints.NotBlank", Map.of());
    }

    public static Constraint JavaxMin() {
        return new Constraint("javax.validation.constraints.Min", Map.of("value", 1));
    }
}
