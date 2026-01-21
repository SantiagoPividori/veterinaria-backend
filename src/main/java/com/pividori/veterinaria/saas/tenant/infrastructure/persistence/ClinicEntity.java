package com.pividori.veterinaria.saas.tenant.infrastructure.persistence;

import com.pividori.veterinaria.saas.tenant.domain.*;
import com.pividori.veterinaria.shared.UserId;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "clinics")
public class ClinicEntity {

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

}
