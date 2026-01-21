package com.pividori.veterinaria.clinic.appointment.infrastructure.in;

import com.pividori.veterinaria.clinic.appointment.infrastructure.in.dtos.AppointmentResponse;
import com.pividori.veterinaria.clinic.appointment.application.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController("/appoitments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

}
