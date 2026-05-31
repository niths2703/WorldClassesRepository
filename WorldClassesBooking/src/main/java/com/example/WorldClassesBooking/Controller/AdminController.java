package com.example.WorldClassesBooking.Controller;

import com.example.WorldClassesBooking.DTOs.request.admin.RegisterRequest;
import com.example.WorldClassesBooking.DTOs.response.admin.RegisterResponse;
import com.example.WorldClassesBooking.Service.RegisterService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    RegisterService registerService;

    public AdminController(RegisterService registerService){
        this.registerService = registerService;
    }

    @PostMapping("/register-user")
    public ResponseEntity<RegisterResponse> registerUserAsTeacherOrParent(@RequestBody RegisterRequest request) throws BadRequestException {
        return ResponseEntity.ok(registerService.registerUserAsStudentOrTeacher(request));
    }
}
