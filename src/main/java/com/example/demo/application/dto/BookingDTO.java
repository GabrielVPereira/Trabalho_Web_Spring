package com.example.demo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
        private Long id;
        private Long userId;
        private Long courtId;
        private Instant startAt;
        private Instant endAt;
        private String status;
}
