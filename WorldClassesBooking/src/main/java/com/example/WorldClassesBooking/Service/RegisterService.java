package com.example.WorldClassesBooking.Service;

import com.example.WorldClassesBooking.DTOs.request.admin.RegisterRequest;
import com.example.WorldClassesBooking.DTOs.response.admin.RegisterResponse;
import org.apache.coyote.BadRequestException;

public interface RegisterService {
    public RegisterResponse registerUserAsStudentOrTeacher(RegisterRequest request) throws BadRequestException;
}
