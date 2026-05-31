package com.example.WorldClassesBooking.Service.Impl;

import com.example.WorldClassesBooking.DTOs.request.admin.AddSessionRequest;
import com.example.WorldClassesBooking.DTOs.request.teacher.TeacherOfferingRequest;
import com.example.WorldClassesBooking.DTOs.response.admin.AddSessionResponse;
import com.example.WorldClassesBooking.DTOs.request.teacher.OfferingRequest;
import com.example.WorldClassesBooking.DTOs.response.teacher.OfferingResponse;
import com.example.WorldClassesBooking.DTOs.response.teacher.TeacherOfferingResponse;
import com.example.WorldClassesBooking.Entity.Course;
import com.example.WorldClassesBooking.Entity.Offering;
import com.example.WorldClassesBooking.Entity.Session;
import com.example.WorldClassesBooking.Entity.Teacher;
import com.example.WorldClassesBooking.Exceptions.*;
import com.example.WorldClassesBooking.Repository.CourseRepository;
import com.example.WorldClassesBooking.Repository.OfferingRepository;
import com.example.WorldClassesBooking.Repository.SessionRepository;
import com.example.WorldClassesBooking.Repository.TeacherRepository;
import com.example.WorldClassesBooking.Service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {

    CourseRepository courseRepository;
    TeacherRepository teacherRepository;
    OfferingRepository offeringRepository;
    SessionRepository sessionRepository;

    public TeacherServiceImpl(SessionRepository sessionRepository,
                              OfferingRepository offeringRepository,
                              CourseRepository courseRepository,
                              TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.offeringRepository = offeringRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public OfferingResponse createOffer(OfferingRequest offeringRequest) {

        log.info("Received createOffer request. Course={}, Teacher={}, Mobile={}, Offering={}",
                offeringRequest.getCourseName(),
                offeringRequest.getTeacherName(),
                offeringRequest.getTeacherMobile(),
                offeringRequest.getOfferingName());

        log.info("Searching course by name: {}", offeringRequest.getCourseName());

        Optional<Course> courseOptional =
                courseRepository.findByName(offeringRequest.getCourseName());

        if (courseOptional.isEmpty()) {
            log.error("Course not found. CourseName={}",
                    offeringRequest.getCourseName());

            throw new InvalidCourseException(
                    "No course exists by name : " + offeringRequest.getCourseName());
        }

        log.info("Course found successfully. CourseName={}",
                offeringRequest.getCourseName());

        log.info("Searching teacher. Name={}, Mobile={}",
                offeringRequest.getTeacherName(),
                offeringRequest.getTeacherMobile());

        Optional<Teacher> teacherOptional =
                teacherRepository.findByNameAndMobile(
                        offeringRequest.getTeacherName(),
                        offeringRequest.getTeacherMobile());
        log.info("Checking Teacher data");

        if (teacherOptional.isEmpty()) {

            log.error("Teacher not found. Name={}, Mobile={}",
                    offeringRequest.getTeacherName(),
                    offeringRequest.getTeacherMobile());

            throw new InvalidUserException(
                    "No teacher exists with this name and mobile.");
        }

        log.info("Teacher found successfully.");

        if (offeringRepository
                .existsByTeacher_NameAndTeacher_MobileAndCourse_Name(
                        offeringRequest.getTeacherName(),
                        offeringRequest.getTeacherMobile(),
                        offeringRequest.getCourseName())) {

            throw new DedupeException(
                    "Offering already exists for this teacher and course.");
        }

        Course course = courseOptional.get();
        Teacher teacher = teacherOptional.get();

        Offering offering = Offering.builder()
                .teacher(teacher)
                .course(course)
                .name(offeringRequest.getOfferingName())
                .build();

        log.info("Saving offering. OfferingName={}",
                offeringRequest.getOfferingName());

        offering = offeringRepository.save(offering);

        log.info("Offering created successfully. OfferingId={}, OfferingName={}",
                offering.getId(),
                offering.getName());

        OfferingResponse response = OfferingResponse.builder()
                .course(course.getName())
                .id(offering.getId())
                .teacher(teacher.getName())
                .name(offering.getName())
                .build();

        log.info("Returning createOffer response. OfferingId={}",
                offering.getId());

        return response;
    }

    @Override
    public AddSessionResponse addSessionsToOffering(
            AddSessionRequest addSessionRequest) {

        log.info("Received addSessionsToOffering request. OfferingName={}, StartTime={}, EndTime={}",
                addSessionRequest.getOfferingName(),
                addSessionRequest.getStartTime(),
                addSessionRequest.getEndTime());

        log.info("Searching offering by name: {}",
                addSessionRequest.getOfferingName());

        Optional<Offering> offeringOptional =
                offeringRepository.findByName(
                        addSessionRequest.getOfferingName());

        if (offeringOptional.isEmpty()) {

            log.error("Offering not found. OfferingName={}",
                    addSessionRequest.getOfferingName());

            throw new InvalidOfferingException(
                    "No such Offering exists");
        }

        boolean overlap =
                sessionRepository.existsOverlappingSession(
                        offeringOptional.get().getId(),
                        addSessionRequest.getStartTime(),
                        addSessionRequest.getEndTime());

        if (overlap) {

            log.warn(
                    "Overlapping session detected for OfferingId={}, Start={}, End={}",
                    offeringOptional.get().getId(),
                    addSessionRequest.getStartTime(),
                    addSessionRequest.getEndTime());

            throw new InvalidSessionException(
                    "Session timing overlaps with an existing session.");
        }

        log.info("Offering found successfully.");

        Session session = Session.builder()
                .offering(offeringOptional.get())
                .startTime(addSessionRequest.getStartTime())
                .endTime(addSessionRequest.getEndTime())
                .build();

        log.info("Saving session for offering: {}",
                addSessionRequest.getOfferingName());

        session = sessionRepository.save(session);

        log.info("Session created successfully. SessionId={}, OfferingName={}",
                session.getId(),
                session.getOffering().getName());

        AddSessionResponse response = AddSessionResponse.builder()
                .id(session.getId())
                .offeringName(session.getOffering().getName())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .build();

        log.info("Returning session response for OfferingName={}",
                session.getOffering().getName());

        return response;
    }

    @Override
    public TeacherOfferingResponse getTeacherOfferingResponse(
            TeacherOfferingRequest teacherOfferingRequest) {

        log.info("Received teacher offering request. TeacherName={}, Mobile={}",
                teacherOfferingRequest.getTeacherName(),
                teacherOfferingRequest.getTeacherMobile());

        List<Offering> offerings =
                offeringRepository.findByTeacherNameAndTeacherMobile(
                        teacherOfferingRequest.getTeacherName(),
                        teacherOfferingRequest.getTeacherMobile());

        log.info("Found {} offerings for TeacherName={}, Mobile={}",
                offerings.size(),
                teacherOfferingRequest.getTeacherName(),
                teacherOfferingRequest.getTeacherMobile());

        List<Course> courses = offerings.stream().map(o-> o.getCourse()).toList();

        TeacherOfferingResponse response =
                TeacherOfferingResponse.builder()
                        .offeringCourses(courses)
                        .build();

        log.info("Returning teacher offering response with {} offerings",
                offerings.size());

        return response;
    }
}