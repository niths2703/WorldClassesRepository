package com.example.WorldClassesBooking.Service;

import com.example.WorldClassesBooking.DTOs.request.parent.AvailableOfferingsRequest;
import com.example.WorldClassesBooking.DTOs.request.parent.BookOfferingRequest;
import com.example.WorldClassesBooking.DTOs.request.parent.ViewBookingsRequest;
import com.example.WorldClassesBooking.DTOs.response.parent.AvailableOfferingsResponse;
import com.example.WorldClassesBooking.DTOs.response.parent.BookOfferingResponse;
import com.example.WorldClassesBooking.DTOs.response.parent.ViewBookingsResponse;
import org.apache.coyote.BadRequestException;

public interface ParentService {

    AvailableOfferingsResponse getAllOfferings();

    ViewBookingsResponse viewMyBookings(ViewBookingsRequest request);
    BookOfferingResponse bookOffering(BookOfferingRequest request) throws BadRequestException;


}
