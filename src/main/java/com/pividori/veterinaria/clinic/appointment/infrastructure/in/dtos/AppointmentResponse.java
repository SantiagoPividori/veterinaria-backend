package com.pividori.veterinaria.clinic.appointment.infrastructure.in.dtos;

import java.time.LocalDate;

public record AppointmentResponse(
        Long vetId,
        Long petId,
        LocalDate startAt,
        String reason
) {
}
