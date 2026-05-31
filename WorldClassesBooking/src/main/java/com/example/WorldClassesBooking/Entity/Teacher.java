package com.example.WorldClassesBooking.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teachers")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String timezone;

    private String email;

    private String mobile;
}
