package com.example.WorldClassesBooking.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "offerings",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"teacher_id", "course_id"}
                )
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Course course;
    @ManyToOne
    private Teacher teacher;
    private String name;
}
