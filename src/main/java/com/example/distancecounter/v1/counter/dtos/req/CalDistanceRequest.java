package com.example.distancecounter.v1.counter.dtos.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CalDistanceRequest {
    @NotBlank(message = "postCodeA is required")
    private String postcodeA;
    @NotBlank(message = "postCodeB is required")
    private String postcodeB;
}
