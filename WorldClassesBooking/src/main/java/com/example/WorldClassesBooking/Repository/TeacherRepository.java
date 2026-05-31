package com.example.WorldClassesBooking.Repository;

import com.example.WorldClassesBooking.Entity.Teacher;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByEmailOrMobile(String email, String mobile);

    boolean existsByNameAndMobile(String name, String mobile);

    Optional<Teacher> findByNameAndMobile(String name, String mobile);

}
