package com.pividori.veterinaria.clinic.appointment.application;

import com.pividori.veterinaria.clinic.appointment.infrastructure.in.dtos.AppointmentResponse;

import java.security.Principal;
import java.util.List;

public interface AppointmentService {

    public List<AppointmentResponse> getAppointments(Principal user);

}
