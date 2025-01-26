package com.example.distancecounter.v1.counter;

import com.example.distancecounter.common.bean.ApiResponse;
import com.example.distancecounter.common.models.Location;
import com.example.distancecounter.v1.counter.dtos.req.AddLocationRequest;
import com.example.distancecounter.v1.counter.dtos.req.CalDistanceRequest;
import com.example.distancecounter.v1.counter.dtos.res.CalDistanceResponse;

public interface LocationService {
    ApiResponse<Location> getLocation(String postcode);
    ApiResponse<String> addLocation(AddLocationRequest req);
    ApiResponse<CalDistanceResponse> calDistance(CalDistanceRequest req);
}
