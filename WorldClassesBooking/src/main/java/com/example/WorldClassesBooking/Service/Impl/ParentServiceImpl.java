package com.example.WorldClassesBooking.Service.Impl;

import com.example.WorldClassesBooking.DTOs.request.parent.AvailableOfferingsRequest;
import com.example.WorldClassesBooking.DTOs.request.parent.BookOfferingRequest;
import com.example.WorldClassesBooking.DTOs.request.parent.ViewBookingsRequest;
import com.example.WorldClassesBooking.DTOs.response.parent.*;
import com.example.WorldClassesBooking.Entity.Booking;
import com.example.WorldClassesBooking.Entity.Offering;
import com.example.WorldClassesBooking.Entity.Parent;
import com.example.WorldClassesBooking.Entity.Session;
import com.example.WorldClassesBooking.Exceptions.*;
import com.example.WorldClassesBooking.Repository.BookingRepository;
import com.example.WorldClassesBooking.Repository.OfferingRepository;
import com.example.WorldClassesBooking.Repository.ParentRepository;
import com.example.WorldClassesBooking.Repository.SessionRepository;
import com.example.WorldClassesBooking.Service.ParentService;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Service
public class ParentServiceImpl implements ParentService {

    OfferingRepository offeringRepository;
    ParentRepository parentRepository;

    BookingRepository bookingRepository;

    SessionRepository sessionRepository;

    public ParentServiceImpl(ParentRepository parentRepository, BookingRepository bookingRepository,SessionRepository sessionRepository, OfferingRepository offeringRepository) {
        this.offeringRepository = offeringRepository;
        this.sessionRepository=sessionRepository;
        this.bookingRepository= bookingRepository;
        this.parentRepository=parentRepository;
    }

    public AvailableOfferingsResponse getAllOfferings() {
        List<Offering> offeringList = offeringRepository.findAvailableOfferings(
                Instant.now());
        return AvailableOfferingsResponse.builder()
                .offeringList(offeringList).build();
    }

    @Override
    public ViewBookingsResponse viewMyBookings(
            ViewBookingsRequest request) {

        Parent parent = parentRepository
                .findByNameAndMobile(
                        request.getParentName(),
                        request.getParentMobile())
                .orElseThrow(() ->
                        new InvalidUserException(
                                "Parent not found"));

        List<BookingResponse> bookingResponses =
                bookingRepository.findByParentId(parent.getId())
                        .stream()
                        .map(booking -> {

                            ZoneId zoneId =
                                    ZoneId.of(
                                            booking.getParent()
                                                    .getTimezone());

                            return BookingResponse.builder()
                                    .bookingId(
                                            booking.getId())
                                    .bookingTime(
                                            booking.getBookingTime()
                                                    .atZone(zoneId))
                                    .course(
                                            booking.getOffering()
                                                    .getCourse()
                                                    .getName())
                                    .offering(
                                            booking.getOffering()
                                                    .getName())
                                    .teacher(
                                            booking.getOffering()
                                                    .getTeacher()
                                                    .getName())
                                    .build();
                        })
                        .toList();

        return ViewBookingsResponse.builder()
                .bookingList(bookingResponses)
                .build();
    }

    @Transactional
    public BookOfferingResponse bookOffering(BookOfferingRequest request) throws BadRequestException {

        //Step 1 Validate Timezone
        ZoneId zoneId;

        try {
            zoneId = ZoneId.of(request.getTimezone());
        } catch (DateTimeException ex) {
            throw new BadRequestException(
                    "Invalid timezone");
        }

        // Step 2 Validate Parent
        Parent parent = parentRepository
                .findByNameAndMobile(request.getParent(),request.getParentMobile())
                .orElseThrow(() ->
                        new InvalidUserException(
                                "Parent not found"));

        // Step 2.1- LOCK
        parent = parentRepository
                .lockParent(parent.getId())
                .orElseThrow(() ->
                        new InvalidUserException("Parent not found"));

        //Step 3 Validate Offering
        Offering offering = offeringRepository
                .findByNameAndCourseName(
                        request.getOffering(),
                        request.getCourse())
                .orElseThrow(() ->
                        new InvalidOfferingException(
                                "Offering not found"));

        //Step 4 Fetch Upcoming Sessions
        // Since session times are stored in UTC: Instant.now is sufficient

        Instant now = Instant.now();

        List<Session> upcomingSessions =
                sessionRepository
                        .findByOfferingIdAndStartTimeAfter(
                                offering.getId(),
                                now);

        //Step 5 Check Offering Availability
        if (upcomingSessions.isEmpty()) {
            throw new InvalidSessionException(
                    "No future sessions available for this offering");
        }

        // Step 6 Check Duplicate Booking
        boolean alreadyBooked =
                bookingRepository
                        .existsByParentIdAndOfferingId(
                                parent.getId(),
                                offering.getId());

        if (alreadyBooked) {
            throw new InvalidBookingException(
                    "Offering already booked");
        }

        //Step 7 Conflict Detection
        // Fetch all existing bookings.

        List<Booking> existingBookings =
                bookingRepository.findByParentId(
                        parent.getId());

        // for every booked offering:

        for (Booking existingBooking : existingBookings) {

            //  fetching sessions:

            List<Session> bookedSessions =
                    sessionRepository.findByOfferingId(
                            existingBooking
                                    .getOffering()
                                    .getId());

            //compare with new offering sessions:

            for (Session bookedSession : bookedSessions) {

                for (Session newSession : upcomingSessions) {

                    boolean overlap =
                            bookedSession.getStartTime()
                                    .isBefore(newSession.getEndTime())
                                    &&
                                    bookedSession.getEndTime()
                                            .isAfter(newSession.getStartTime());

//                    Overlap formula:
//
//                    start1 < end2
//                            AND
//                    end1 > start2

                    if (overlap) {

                        throw new ConflictException(
                                "Booking conflicts with existing booked offering");
                    }
                }
            }
        }

       // Step 8 Create Booking
        Booking booking = new Booking();

        booking.setParent(parent);
        booking.setOffering(offering);
        booking.setBookingTime(Instant.now());

        bookingRepository.save(booking);

        //Step 9: Create response sessions.
        List<SessionResponse> responseSessions =
                upcomingSessions.stream()
                        .map(session ->
                                SessionResponse.builder()
                                        .startTime(
                                                session.getStartTime()
                                                        .atZone(zoneId))
                                        .endTime(
                                                session.getEndTime()
                                                        .atZone(zoneId))
                                        .build())
                        .toList();

        //  Step 10 Return Response
        return BookOfferingResponse.builder()
                .parent(parent.getName())
                .course(offering.getCourse().getName())
                .offering(offering.getName())
                .sessions(responseSessions)
                .build();
    }
}