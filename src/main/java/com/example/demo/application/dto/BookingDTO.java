package com.example.demo.application.dto;

import java.time.Instant;

public record BookingDTO(
        Long id,
        Long userId,
        Long courtId,
        Instant startAt,
        Instant endAt,
        String status
) {}
