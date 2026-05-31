package com.example.WorldClassesBooking.DTOs.response.teacher;

import com.example.WorldClassesBooking.Entity.Course;
import com.example.WorldClassesBooking.Entity.Offering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherOfferingResponse {
    List<Course> offeringCourses;
}
