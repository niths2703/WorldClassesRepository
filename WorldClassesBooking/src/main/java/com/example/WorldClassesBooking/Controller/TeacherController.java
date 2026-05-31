package com.example.WorldClassesBooking.Controller;

import com.example.WorldClassesBooking.DTOs.request.admin.AddSessionRequest;
import com.example.WorldClassesBooking.DTOs.request.teacher.TeacherOfferingRequest;
import com.example.WorldClassesBooking.DTOs.response.admin.AddSessionResponse;
import com.example.WorldClassesBooking.DTOs.request.teacher.OfferingRequest;
import com.example.WorldClassesBooking.DTOs.response.teacher.OfferingResponse;
import com.example.WorldClassesBooking.DTOs.response.teacher.TeacherOfferingResponse;
import com.example.WorldClassesBooking.Service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    TeacherService teacherService;
    public TeacherController(TeacherService teacherService){
        this.teacherService= teacherService;
    }

    @PostMapping("/create-offering")
    public ResponseEntity<OfferingResponse> createOffering(@RequestBody OfferingRequest offeringRequest){
     return ResponseEntity.ok(teacherService.createOffer(offeringRequest));
    }

    @PostMapping("/add-sessions")
    public ResponseEntity<AddSessionResponse> addSessionsToOfferings(@RequestBody AddSessionRequest addSessionRequest){
      return ResponseEntity.ok(teacherService.addSessionsToOffering(addSessionRequest));
    }

    @PostMapping("/view-offerings")
    public ResponseEntity<TeacherOfferingResponse> getOfferingsForTeacher(@RequestBody TeacherOfferingRequest teacherOfferingRequest) throws BadRequestException {
        return ResponseEntity.ok(teacherService.getTeacherOfferingResponse(teacherOfferingRequest));
    }
}
