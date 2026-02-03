package com.pividori.veterinaria.saas.tenant.infrastructure.rest.mapper;

import com.pividori.veterinaria.saas.tenant.application.port.in.command.RegisterClinicCommand;
import com.pividori.veterinaria.saas.tenant.application.port.in.result.RegisterClinicResult;
import com.pividori.veterinaria.saas.tenant.infrastructure.rest.dto.RegisterClinicRequest;
import com.pividori.veterinaria.saas.tenant.infrastructure.rest.dto.RegisterClinicResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClinicWebMapper {

    /*   Usuario   */
    @Mapping(target = "ownerEmail", source = "ownerEmail")
    @Mapping(target = "ownerPassword", source = "ownerPassword")
    @Mapping(target = "ownerFirstName", source = "ownerFirstName")
    @Mapping(target = "ownerLastName", source = "ownerLastName")
    @Mapping(target = "ownerBirthDate", source = "ownerBirthDate")
    /*   Clínica   */
    @Mapping(target = "name", source = "name")
    @Mapping(target = "legalName", source = "legalName")
    @Mapping(target = "cuit", source = "cuit")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "street", source = "street")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "province", source = "province")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "planType", source = "planType")
    @Mapping(target = "timeZone", source = "timeZone")
    RegisterClinicCommand toRegisterClinicCommand(RegisterClinicRequest registerClinicRequest);

    RegisterClinicResponse toRegisterClinicResponse(RegisterClinicResult registerClinicResult);

}