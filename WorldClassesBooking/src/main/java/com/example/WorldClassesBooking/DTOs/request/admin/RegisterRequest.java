package com.example.WorldClassesBooking.DTOs.request.admin;

import com.example.WorldClassesBooking.Enum.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    private String mobile;
    private String email;
    private UserRole userRole;
    private String timeZone;
}
