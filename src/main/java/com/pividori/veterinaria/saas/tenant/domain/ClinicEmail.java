package com.pividori.veterinaria.saas.tenant.domain;

import com.pividori.veterinaria.shared.exceptions.DomainException;

import java.util.regex.Pattern;

public record ClinicEmail(
        String value
) {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2}$");

    public ClinicEmail {
        if (value == null || value.isEmpty()) {
            throw new DomainException("Email is required");
        }

        value = value.trim().toLowerCase();

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new DomainException("Invalid email");
        }
    }
}
