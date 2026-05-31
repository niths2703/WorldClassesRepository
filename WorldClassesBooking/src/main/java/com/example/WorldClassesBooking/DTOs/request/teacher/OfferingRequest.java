package com.example.WorldClassesBooking.DTOs.request.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferingRequest {
    String courseName;
    String teacherName;
    String teacherMobile;
    String offeringName;
}
