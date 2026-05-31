package com.example.WorldClassesBooking.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String timezone;

    private String email;

    private String mobile;
}
