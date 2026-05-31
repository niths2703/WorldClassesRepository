package com.example.WorldClassesBooking.Service.Impl;

import com.example.WorldClassesBooking.DTOs.request.admin.RegisterRequest;
import com.example.WorldClassesBooking.DTOs.response.admin.RegisterResponse;
import com.example.WorldClassesBooking.Entity.Parent;
import com.example.WorldClassesBooking.Entity.Teacher;
import com.example.WorldClassesBooking.Enum.UserRole;
import com.example.WorldClassesBooking.Exceptions.DedupeException;
import com.example.WorldClassesBooking.Exceptions.InvalidUserRoleException;
import com.example.WorldClassesBooking.Repository.ParentRepository;
import com.example.WorldClassesBooking.Repository.TeacherRepository;
import com.example.WorldClassesBooking.Service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.ZoneId;

@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private TeacherRepository teacherRepository;

    private ParentRepository parentRepository;

    public RegisterServiceImpl(TeacherRepository teacherRepository, ParentRepository parentRepository){
        this.teacherRepository= teacherRepository;
        this.parentRepository= parentRepository;
    }

    /*
    Timezone accepted values :
    Asia/Kolkata
    America/New_York
    Europe/London
    Asia/Singapore
    Australia/Sydney
    UTC
     */
    @Override
    public RegisterResponse registerUserAsStudentOrTeacher(RegisterRequest request)
            throws BadRequestException {

        log.info("Received registration request. Email={}, Mobile={}, Role={}",
                request.getEmail(), request.getMobile(), request.getUserRole());

        try {
            ZoneId.of(request.getTimeZone());
            log.info("Timezone validation successful. Timezone={}", request.getTimeZone());
        } catch (DateTimeException ex) {
            log.error("Invalid timezone received: {}", request.getTimeZone(), ex);
            throw new BadRequestException("Invalid timezone");
        }

        if (UserRole.PARENT.equals(request.getUserRole())) {

            log.info("Processing registration for PARENT. Email={}", request.getEmail());

            boolean exists =
                    parentRepository.existsByEmailOrMobile(request.getEmail(), request.getMobile());

            log.info("Parent duplicate check result: {}", exists);

            if (exists) {
                log.warn("Duplicate parent registration attempt. Email={}, Mobile={}",
                        request.getEmail(), request.getMobile());
                throw new DedupeException("User already exists with this email or mobile.");
            }

            Parent parent = Parent.builder()
                    .email(request.getEmail())
                    .name(request.getName())
                    .mobile(request.getMobile())
                    .timezone(request.getTimeZone())
                    .build();

            log.info("Saving parent user. Email={}", parent.getEmail());

            parent = parentRepository.save(parent);

            log.info("Parent registration successful. ParentId={}", parent.getId());

            RegisterResponse response = RegisterResponse.builder()
                    .userId(parent.getId())
                    .userRole(UserRole.PARENT)
                    .email(parent.getEmail())
                    .mobile(parent.getMobile())
                    .name(parent.getName())
                    .build();

            log.info("Returning registration response for ParentId={}", parent.getId());

            return response;

        } else if (UserRole.TEACHER.equals(request.getUserRole())) {

            log.info("Processing registration for TEACHER. Email={}", request.getEmail());

            boolean exists =
                    teacherRepository.existsByEmailOrMobile(request.getEmail(), request.getMobile());

            log.info("Teacher duplicate check result: {}", exists);

            if (exists) {
                log.warn("Duplicate teacher registration attempt. Email={}, Mobile={}",
                        request.getEmail(), request.getMobile());
                throw new DedupeException("User already exists with this email or mobile.");
            }

            Teacher teacher = Teacher.builder()
                    .email(request.getEmail())
                    .name(request.getName())
                    .mobile(request.getMobile())
                    .timezone(request.getTimeZone())
                    .build();

            log.info("Saving teacher user. Email={}", teacher.getEmail());

            teacher = teacherRepository.save(teacher);

            log.info("Teacher registration successful. TeacherId={}", teacher.getId());

            RegisterResponse response = RegisterResponse.builder()
                    .userId(teacher.getId())
                    .userRole(UserRole.TEACHER)
                    .email(teacher.getEmail())
                    .mobile(teacher.getMobile())
                    .name(teacher.getName())
                    .build();

            log.info("Returning registration response for TeacherId={}", teacher.getId());

            return response;

        } else {

            log.error("Invalid user role received: {}", request.getUserRole());

            throw new InvalidUserRoleException("No such user role exists");
        }
    }
}
