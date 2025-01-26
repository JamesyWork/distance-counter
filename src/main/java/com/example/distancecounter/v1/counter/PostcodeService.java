package com.example.distancecounter.v1.counter;

import com.example.distancecounter.common.bean.ApiResponse;
import com.example.distancecounter.common.models.Location;
import com.example.distancecounter.v1.counter.dtos.req.AddPostcodeRequest;
import com.example.distancecounter.v1.counter.dtos.req.CalDistanceRequest;
import com.example.distancecounter.v1.counter.dtos.res.CalDistanceResponse;

public interface PostcodeService {
    ApiResponse<Location> getPostcode(String postcode);
    ApiResponse<String> addPostcode(AddPostcodeRequest req);
    ApiResponse<CalDistanceResponse> calDistance(CalDistanceRequest req);
}
