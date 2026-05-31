package com.example.WorldClassesBooking.DTOs.response.parent;


import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Builder
@Data
public class BookingResponse {

        private Long bookingId;
        private ZonedDateTime bookingTime;
        private String course;
        private String offering;
        private String teacher;

}
