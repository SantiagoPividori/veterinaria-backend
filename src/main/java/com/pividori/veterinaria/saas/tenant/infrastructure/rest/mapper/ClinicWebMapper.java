package com.pividori.veterinaria.saas.tenant.infrastructure.rest.mapper;

import com.pividori.veterinaria.saas.tenant.application.port.in.command.RegisterClinicCommand;
import com.pividori.veterinaria.saas.tenant.application.port.in.result.RegisterClinicResult;
import com.pividori.veterinaria.saas.tenant.infrastructure.rest.dto.RegisterClinicRequest;
import com.pividori.veterinaria.saas.tenant.infrastructure.rest.dto.RegisterClinicResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClinicWebMapper {

    @Mapping()
    RegisterClinicCommand toRegisterClinicCommand(RegisterClinicRequest registerClinicRequest);

    RegisterClinicResponse toRegisterClinicResponse(RegisterClinicResult registerClinicResult);

}