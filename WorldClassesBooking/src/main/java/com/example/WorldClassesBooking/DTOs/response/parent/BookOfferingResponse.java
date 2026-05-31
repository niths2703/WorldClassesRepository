package com.example.WorldClassesBooking.DTOs.response.parent;

import com.example.WorldClassesBooking.Entity.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookOfferingResponse {
    private String parent;
    private String course;
    private String offering;
    private List<SessionResponse> sessions;
}
