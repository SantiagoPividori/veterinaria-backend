package com.pividori.veterinaria.saas.tenant.domain.model;

import com.pividori.veterinaria.saas.tenant.domain.enums.ClinicStatus;
import com.pividori.veterinaria.saas.tenant.domain.enums.PlanType;
import com.pividori.veterinaria.saas.tenant.domain.valueobject.*;
import com.pividori.veterinaria.shared.UserId;
import com.pividori.veterinaria.shared.exceptions.DomainException;

import java.time.Instant;
import java.time.ZoneId;

public class Clinic {

    private final ClinicId id;
    private ClinicName name;
    private ClinicLegalName legalName;
    private ClinicCuit cuit;
    private ClinicEmail email;
    private ClinicPhoneNumber phoneNumber;
    private ClinicAddress address;
    private ClinicStatus status;
    private PlanType planType;
    private final UserId ownerId;
    private ZoneId timeZone;
    private final Instant createdAt;
    private Instant updatedAt;

    public Clinic(ClinicId id, ClinicName name, ClinicLegalName legalName, ClinicCuit cuit, ClinicEmail email, ClinicPhoneNumber phoneNumber,
                  ClinicAddress address, ClinicStatus status, PlanType planType, UserId ownerId, ZoneId timeZone, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.legalName = legalName;
        this.cuit = cuit;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.planType = planType;
        this.ownerId = ownerId;
        this.timeZone = timeZone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static Clinic create(ClinicName name, ClinicLegalName legalName, ClinicCuit cuit, ClinicEmail email,
                                ClinicPhoneNumber phoneNumber, ClinicAddress address, PlanType planType, UserId ownerId) {
        Instant now = Instant.now();
        return new Clinic(
                ClinicId.generate(),
                name,
                legalName,
                cuit,
                email,
                phoneNumber,
                address,
                ClinicStatus.PENDING_VERIFICATION,
                planType,
                ownerId,
                ZoneId.of("America/Argentina/Buenos_Aires"),
                now,
                now
        );
    }

    public static Clinic create(ClinicName name, ClinicLegalName legalName, ClinicCuit cuit,
                                ClinicEmail email, PlanType planType, UserId ownerId) {
        Instant now = Instant.now();
        return new Clinic(
                ClinicId.generate(),
                name,
                legalName,
                cuit,
                email,
                null,
                null,
                ClinicStatus.PENDING_VERIFICATION,
                planType,
                ownerId,
                ZoneId.of("America/Argentina/Buenos_Aires"),
                now,
                now
        );
    }

    public static Clinic reconstitute(ClinicId id, ClinicName name, ClinicLegalName legalName, ClinicCuit cuit, ClinicEmail email,
                                      ClinicPhoneNumber phoneNumber, ClinicAddress address, ClinicStatus status,
                                      PlanType planType, UserId ownerId, ZoneId timeZone, Instant createdAt, Instant updatedAt) {
        return new Clinic(
                id,
                name,
                legalName,
                cuit,
                email,
                phoneNumber,
                address,
                status,
                planType,
                ownerId,
                timeZone,
                createdAt,
                updatedAt
        );
    }

    public void updateContactInfo(ClinicEmail newEmail, ClinicPhoneNumber newPhoneNumber, ClinicAddress newAddress) {
        if (status.canUpdateContactInfo()) {
            throw new DomainException("Contact info cannot be updated in current status");
        }
        if (newEmail != null) {
            this.email = newEmail;
        }
        if (newPhoneNumber != null) {
            this.phoneNumber = newPhoneNumber;
        }
        if (newAddress != null) {
            this.address = newAddress;
        }
        touch();
    }

    public void upgradePlan(PlanType newPlanType) {
        if (status.canChangedPlan()) {
            throw new DomainException("Plan cannot be upgrade in current status");
        }
        if (this.planType == newPlanType) {
            throw new DomainException("Cannot be changed plan type to the same plan type");
        }
        this.planType = newPlanType;
        touch();
    }

    public void activate() {
        if (this.status == ClinicStatus.ACTIVE) {
            throw new DomainException("The clinic is already active");
        }
        this.status = ClinicStatus.ACTIVE;
        touch();
    }

    public void suspend() {
        if (this.status == ClinicStatus.SUSPENDED) {
            throw new DomainException("The clinic is already suspended");
        }
        this.status = ClinicStatus.SUSPENDED;
        touch();
    }

    public void touch() {
        this.updatedAt = Instant.now();
    }

    public ClinicId getId() {
        return id;
    }

    public ClinicName getName() {
        return name;
    }

    public ClinicLegalName getLegalName() {
        return legalName;
    }

    public ClinicCuit getCuit() {
        return cuit;
    }

    public ClinicEmail getEmail() {
        return email;
    }

    public ClinicPhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public ClinicAddress getAddress() {
        return address;
    }

    public ClinicStatus getStatus() {
        return status;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public UserId getOwnerId() {
        return ownerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
