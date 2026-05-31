package com.example.WorldClassesBooking.DTOs.response.teacher;

import com.example.WorldClassesBooking.Entity.Course;
import com.example.WorldClassesBooking.Entity.Teacher;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferingResponse {

    private Long id;
    private String course;
    private String teacher;
    private String name;
}
