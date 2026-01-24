package com.pividori.veterinaria.saas.tenant.domain.valueobject;

import com.pividori.veterinaria.shared.exceptions.DomainException;

import java.util.regex.Pattern;

public record ClinicPhoneNumber(
        String value
) {

    private static final Pattern PHONE_NUMBER_PATTERN =
            Pattern.compile("^\\+?[0-9]{7,15}$");

    public ClinicPhoneNumber {

        if (value == null || value.isBlank()) {
            throw new DomainException("Phone number is required");
        }

        value = value.replaceAll("[\\s\\-()]", "");

        if (!PHONE_NUMBER_PATTERN.matcher(value).matches()) {
            throw new DomainException("Invalid phone number");
        }
    }
}
