package com.pividori.veterinaria.saas.tenant.infrastructure.persistence.mapper;

import com.pividori.veterinaria.saas.tenant.domain.*;
import com.pividori.veterinaria.saas.tenant.domain.model.Clinic;
import com.pividori.veterinaria.saas.tenant.infrastructure.persistence.ClinicEntity;
import com.pividori.veterinaria.shared.UserId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface ClinicPersistenceMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.value")
    @Mapping(target = "legalName", source = "legalName.value")
    @Mapping(target = "cuit", source = "cuit.value")
    @Mapping(target = "email", source = "email.value")
    @Mapping(target = "phoneNumber", source = "phoneNumber.value")
    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "province", source = "address.province")
    @Mapping(target = "postalCode", source = "address.postalCode")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "planType", source = "planType")
    @Mapping(target = "ownerId", source = "ownerId.value")
    @Mapping(target = "timeZone", source = "timeZone")
    @Mapping(target = "createAt", source = "createAt")
    @Mapping(target = "updateAt", source = "updateAt")
    ClinicEntity toEntity(Clinic clinic);

    default Clinic toDomain(ClinicEntity clinicEntity) {
        if (clinicEntity == null) return null;

        return Clinic.reconstitute(new ClinicId(clinicEntity.getId()),
                new ClinicName(clinicEntity.getName()),
                new ClinicLegalName(clinicEntity.getLegalName()),
                new ClinicCuit(clinicEntity.getCuit()),
                new ClinicEmail(clinicEntity.getEmail()),
                new ClinicPhoneNumber(clinicEntity.getPhoneNumber()),
                new ClinicAddress(clinicEntity.getStreet(),
                        clinicEntity.getCity(),
                        clinicEntity.getProvince(),
                        clinicEntity.getPostalCode()),
                mapStatus(clinicEntity.getStatus()),
                mapPlanType(clinicEntity.getPlanType()),
                new UserId(clinicEntity.getOwnerId()),
                ZoneId.of(clinicEntity.getTimeZone()),
                clinicEntity.getCreatedAt(),
                clinicEntity.getUpdatedAt()
        );
    }

    default ClinicStatus mapStatus(String status) {
        return status != null ? ClinicStatus.valueOf(status) : ClinicStatus.PENDING_VERIFICATION;
    }

    default PlanType mapPlanType(String planType) {
        return planType != null ? PlanType.valueOf(planType) : PlanType.FREE;
    }

}