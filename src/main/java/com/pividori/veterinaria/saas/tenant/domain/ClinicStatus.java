package com.pividori.veterinaria.saas.tenant.domain;

public enum ClinicStatus {
    PENDING_VERIFICATION,
    ACTIVE,
    SUSPENDED,
    TERMINATED;

    public boolean canUpdateContactInfo() {
        return this == PENDING_VERIFICATION || this == ACTIVE;
    }

    public boolean canChangedPlan() {
        return this == ACTIVE;
    }

}
