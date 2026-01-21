package com.pividori.veterinaria.saas.tenant.domain;

import java.util.UUID;

public record ClinicId(
        UUID value
) {
    public static ClinicId generate(){
        return new ClinicId(UUID.randomUUID());
    }
}
