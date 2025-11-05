package com.example.demo.application.dto;

import jakarta.validation.constraints.NotBlank;

public record BookingUpdateDTO(
        @NotBlank String status // "CONFIRMED" | "CANCELED"
) {}
