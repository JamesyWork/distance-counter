package com.example.distancecounter.v1.counter;

import com.example.distancecounter.common.bean.ApiResponse;
import com.example.distancecounter.common.models.Location;
import com.example.distancecounter.v1.counter.dtos.req.AddLocationRequest;
import com.example.distancecounter.v1.counter.dtos.req.CalDistanceRequest;
import com.example.distancecounter.v1.counter.dtos.res.CalDistanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/location")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ApiResponse<String> addLocation(@RequestBody @Validated AddLocationRequest req) {
        return this.locationService.addLocation(req);
    }

    @GetMapping("{postcode}")
    public ApiResponse<Location> getPostcode(@PathVariable(name = "postcode") String postcode) {
        return this.locationService.getLocation(postcode);
    }

    @PostMapping("cal-distance")
    public ApiResponse<CalDistanceResponse> calculateDistance(@Validated @RequestBody CalDistanceRequest req) {
        return this.locationService.calDistance(req);
    }
}
