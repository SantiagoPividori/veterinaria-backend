package com.pividori.veterinaria.saas.iam.domain.valueobject;

import com.pividori.veterinaria.shared.exceptions.DomainException;

import java.util.regex.Pattern;

public record UserName(
        String value
) {

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[\\\\p{L} .'-]+$");

    public UserName{

        if (value == null || value.isBlank()) {
            throw new DomainException("Name is required");
        }
        if (value.length() < 2 || value.length() > 50) {
            throw new DomainException("Name must be between 2 and 50 characters");
        }
        if(!NAME_PATTERN.matcher(value).matches()) {
            throw new DomainException("Invalid name");
        }

        value = value.trim().substring(0, 1).toUpperCase() + value.trim().substring(1).toLowerCase();
    }
}
