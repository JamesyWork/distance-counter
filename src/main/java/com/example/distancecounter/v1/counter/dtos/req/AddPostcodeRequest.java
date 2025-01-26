package com.example.distancecounter.v1.counter.dtos.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddPostcodeRequest {
    @NotBlank(message = "postcode is required")
    private String postcode;
    private double latitude;
    private double longitude;
}
