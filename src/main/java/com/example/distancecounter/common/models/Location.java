package com.example.distancecounter.common.models;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String postcode;
    private double latitude;
    private double longitude;
}
