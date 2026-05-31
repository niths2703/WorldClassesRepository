package com.example.WorldClassesBooking.DTOs.request.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherOfferingRequest {
    String teacherName;
    String teacherMobile;
}
