package com.example.WorldClassesBooking.DTOs.response.parent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponse {
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}
