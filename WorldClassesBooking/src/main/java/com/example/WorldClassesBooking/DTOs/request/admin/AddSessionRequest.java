package com.example.WorldClassesBooking.DTOs.request.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSessionRequest {
    String offeringName;
    Instant startTime; //utc
    Instant endTime; //utc
}
