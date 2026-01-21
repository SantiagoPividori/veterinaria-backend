package com.pividori.veterinaria.saas.tenant.domain;

import com.pividori.veterinaria.shared.exceptions.DomainException;

import java.util.regex.Pattern;

public record ClinicCuit(
        String value
) {

    private static final Pattern CUIT_PATTERN =
            Pattern.compile("^\\d{2}-\\d{8}-\\d$");

    public ClinicCuit {

        if (value == null || value.isBlank()) {
            throw new DomainException("CUIT value is required");
        }

        value = value.trim().replaceAll("\\s+", "");

        // Validation of basic format (XX-XXXXXXXX-X)
        if (!CUIT_PATTERN.matcher(value).matches()) {
            throw new DomainException("Invalid format CUIT. Is must be XX-XXXXXXXX-X");
        }

        if (!isValidChecksum(value)) {
            throw new DomainException("El número de CUIT no es válido (error de dígito verificador)");
        }
    }

    // This is for verification of algorithm of module 11 (AFIP)
    private static boolean isValidChecksum(String value) {
        String digits = value.replace("-", "");
        if (digits.length() != 11) return false;

        int[] multipliers = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(digits.charAt(i)) * multipliers[i];
        }

        int result = 11 - (sum % 11);
        if (result == 11) result = 0;
        if (result == 10) result = 9; //Special case

        int lastDigit = Character.getNumericValue(digits.charAt(10));
        return result == lastDigit;
    }

}
