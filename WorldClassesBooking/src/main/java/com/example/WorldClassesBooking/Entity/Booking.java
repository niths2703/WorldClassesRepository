package com.example.WorldClassesBooking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
        name="bookings",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames={"parent_id","offering_id"})
        })
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Parent parent;

    @ManyToOne
    private Offering offering;

    private Instant bookingTime;
}