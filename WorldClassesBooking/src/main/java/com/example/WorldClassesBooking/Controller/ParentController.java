package com.example.WorldClassesBooking.Controller;

import com.example.WorldClassesBooking.DTOs.request.parent.AvailableOfferingsRequest;
import com.example.WorldClassesBooking.DTOs.request.parent.BookOfferingRequest;
import com.example.WorldClassesBooking.DTOs.request.parent.ViewBookingsRequest;
import com.example.WorldClassesBooking.DTOs.response.parent.AvailableOfferingsResponse;
import com.example.WorldClassesBooking.DTOs.response.parent.BookOfferingResponse;
import com.example.WorldClassesBooking.DTOs.response.parent.ViewBookingsResponse;
import com.example.WorldClassesBooking.Service.ParentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parent")
public class ParentController {


    ParentService parentService;
    public ParentController(ParentService parentService){
        this.parentService=parentService;
    }

    @GetMapping("/get-available-offerings")
    public ResponseEntity<AvailableOfferingsResponse> getAvailableOfferings(){
      return ResponseEntity.ok(parentService.getAllOfferings());
    }

    @PostMapping("/view-my-bookings")
    public ResponseEntity<ViewBookingsResponse> viewBookingResponse(@RequestBody ViewBookingsRequest viewBookingsRequest){
       return ResponseEntity.ok(parentService.viewMyBookings(viewBookingsRequest));
    }

    @PostMapping("/book-offering")
    public ResponseEntity<BookOfferingResponse> bookOfferings(@RequestBody BookOfferingRequest bookOfferingRequest) throws BadRequestException {
        return ResponseEntity.ok(parentService.bookOffering(bookOfferingRequest));
    }
}
