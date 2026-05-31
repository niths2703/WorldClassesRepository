package com.example.WorldClassesBooking.DTOs.request.parent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOfferingRequest {
    private String parent;
    private String parentMobile;
    private String course;
    private String offering;
    private String timezone;
}
