package com.pividori.veterinaria.saas.iam.domain.valueobject;

import com.pividori.veterinaria.shared.exceptions.DomainException;

import java.time.LocalDate;
import java.time.Period;

public record BirthDate(
        LocalDate value
) {
    public BirthDate {
        if (value == null) {
            throw new DomainException("Birth Date is required");
        }
        if (value.isAfter(LocalDate.now())) {
            throw new DomainException("Birth Date cannot be in a future");
        }
        if (Period.between(value, LocalDate.now()).getYears() < 18) {
            throw new DomainException("Owner most be at least 18 years old");
        }
    }

}
