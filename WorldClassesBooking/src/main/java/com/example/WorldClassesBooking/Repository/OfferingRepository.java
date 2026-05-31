package com.example.WorldClassesBooking.Repository;

import com.example.WorldClassesBooking.Entity.Offering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OfferingRepository extends JpaRepository<Offering, Long> {
    Optional<Offering> findByName(String name);

    Optional<Offering> findByNameAndCourseName(
            String offeringName,
            String courseName);

    boolean existsByTeacher_NameAndTeacher_MobileAndCourse_Name(
            String teacherName,
            String teacherMobile,
            String courseName);

    List<Offering> findByTeacherNameAndTeacherMobile(
            String name,
            String mobile);

    @Query("""
                SELECT DISTINCT o
                FROM Offering o
                JOIN Session s ON s.offering.id = o.id
                WHERE s.startTime > :now
            """)
    List<Offering> findAvailableOfferings(
            @Param("now") Instant now);
}