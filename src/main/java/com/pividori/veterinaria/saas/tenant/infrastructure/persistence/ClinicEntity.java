package com.pividori.veterinaria.saas.tenant.infrastructure.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "clinics")
public class ClinicEntity {

    @Id
    private UUID id;
    @Column(name = "name",  nullable = false)
    private String name;
    @Column(name = "legal_name", nullable = false)
    private String legalName;
    @Column(name = "cuit",  nullable = false, unique = true)
    private String cuit;
    @Email
    @Column(name = "email",   nullable = false)
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @Column(name = "province")
    private String province;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "plan_type",  nullable = false)
    private String planType;
    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;
    @Column(name = "time_zone")
    private String timeZone;
    @Column(name = "created_at",  nullable = false, updatable = false)
    private Instant createdAt;
    @Column(name = "update_at",   nullable = false)
    private Instant updatedAt;

}
