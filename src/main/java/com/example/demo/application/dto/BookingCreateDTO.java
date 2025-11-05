package com.example.demo.application.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record BookingCreateDTO(
        @NotNull Long userId,
        @NotNull Long courtId,
        @NotNull @Future Instant startAt,
        @NotNull @Future Instant endAt
) {}
