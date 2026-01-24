package com.pividori.veterinaria.saas.iam.domain.valueobject;

import com.pividori.veterinaria.shared.exceptions.DomainException;

import java.util.regex.Pattern;

public record UserEmail(
        String value
) {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public UserEmail {
        if (value == null || value.isBlank()) {
            throw new DomainException("Email is required");
        }

        value = value.trim().toLowerCase();

        if(!EMAIL_PATTERN.matcher(value).matches()) {
            throw new DomainException("Invalid email");
        }

    }
}
