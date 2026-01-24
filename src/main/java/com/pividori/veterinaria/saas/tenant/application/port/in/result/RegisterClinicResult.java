package com.pividori.veterinaria.saas.tenant.application.port.in.result;

import com.pividori.veterinaria.saas.tenant.domain.valueobject.ClinicId;

public record RegisterClinicResult(
        ClinicId clinicId
) {
}
