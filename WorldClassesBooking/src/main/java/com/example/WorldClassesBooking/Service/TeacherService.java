package com.example.WorldClassesBooking.Service;

import com.example.WorldClassesBooking.DTOs.request.admin.AddSessionRequest;
import com.example.WorldClassesBooking.DTOs.request.teacher.TeacherOfferingRequest;
import com.example.WorldClassesBooking.DTOs.response.admin.AddSessionResponse;
import com.example.WorldClassesBooking.DTOs.request.teacher.OfferingRequest;
import com.example.WorldClassesBooking.DTOs.response.teacher.OfferingResponse;
import com.example.WorldClassesBooking.DTOs.response.teacher.TeacherOfferingResponse;
import org.apache.coyote.BadRequestException;

public interface TeacherService {
     OfferingResponse createOffer(OfferingRequest offeringRequest);

     AddSessionResponse addSessionsToOffering(AddSessionRequest addSessionRequest);

     TeacherOfferingResponse getTeacherOfferingResponse(TeacherOfferingRequest teacherOfferingRequest) throws BadRequestException;
}
