package com.example.distancecounter.v1.counter.dtos.res;

import com.example.distancecounter.common.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalDistanceResponse {
    private List<Location> locations;
    private double distance;
    private String unit;

    public CalDistanceResponse(List<Location> locations, double distance) {
        this.locations = locations;
        this.distance = distance;
        this.unit = "km";
    }
}
