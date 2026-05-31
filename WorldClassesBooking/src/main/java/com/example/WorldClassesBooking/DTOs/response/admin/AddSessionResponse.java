package com.example.WorldClassesBooking.DTOs.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddSessionResponse {
    Long id;
    String offeringName;
    Instant startTime;
    Instant endTime;
}
