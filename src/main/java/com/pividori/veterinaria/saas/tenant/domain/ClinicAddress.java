package com.pividori.veterinaria.saas.tenant.domain;

import com.pividori.veterinaria.shared.exceptions.DomainException;

public record ClinicAddress(
        String street,
        String city,
        String province,
        String postalCode
) {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_STREET_LENGTH = 100;
    private static final int MAX_CITY_LENGTH = 50;
    private static final int MAX_PROVINCE_LENGTH = 50;
    private static final int MAX_POSTAL_CODE_LENGTH = 10;

    public ClinicAddress {
        street = validate(street, "street", MAX_STREET_LENGTH);
        city = validate(city, "city", MAX_CITY_LENGTH);
        province = validate(province, "province", MAX_PROVINCE_LENGTH);
        postalCode = validate(postalCode, "postalCode", MAX_POSTAL_CODE_LENGTH);
    }

    private static String validate(String value, String fieldName, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new DomainException(fieldName + " is required");
        }

        //Replace any whitespace, tab, or line breaks with a single space.
        String normalizedValue = value.trim().replaceAll("\\s+", " ");

        if (normalizedValue.length() < MIN_LENGTH) {
            throw new DomainException(fieldName + " too short. Min length is " + MIN_LENGTH);
        }

        if (normalizedValue.length() > maxLength) {
            throw new DomainException(fieldName + " too long. Max length is " + maxLength);
        }

        return normalizedValue;
    }
}
