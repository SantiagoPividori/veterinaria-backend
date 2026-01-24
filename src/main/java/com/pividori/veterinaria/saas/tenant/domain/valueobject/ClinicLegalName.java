package com.pividori.veterinaria.saas.tenant.domain.valueobject;

import com.pividori.veterinaria.shared.exceptions.DomainException;

public record ClinicLegalName(
        String value
) {
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 100;

    public ClinicLegalName {

        if (value == null || value.isBlank()) {
            throw new DomainException("Name is required");
        }

        value = value.trim().replaceAll("\\s+", " ");

        if (value.length() < MIN_LENGTH) {
            throw new DomainException("Name too short. Min length is " + MIN_LENGTH);
        }

        if (value.length() > MAX_LENGTH) {
            throw new DomainException("Name too long. Max length is " + MAX_LENGTH);
        }
    }
}
