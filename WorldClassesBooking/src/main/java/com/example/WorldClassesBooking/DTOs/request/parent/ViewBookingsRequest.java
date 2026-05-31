package com.example.WorldClassesBooking.DTOs.request.parent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewBookingsRequest {
    private String parentName;
    private String parentMobile;
}
