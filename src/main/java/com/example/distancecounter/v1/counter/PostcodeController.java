package com.example.distancecounter.v1.counter;

import com.example.distancecounter.common.bean.ApiResponse;
import com.example.distancecounter.common.models.Location;
import com.example.distancecounter.v1.counter.dtos.req.AddPostcodeRequest;
import com.example.distancecounter.v1.counter.dtos.req.CalDistanceRequest;
import com.example.distancecounter.v1.counter.dtos.res.CalDistanceResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/postcode")
public class PostcodeController {
    private final PostcodeService postcodeService;
    public PostcodeController(PostcodeService postcodeService) {
        this.postcodeService = postcodeService;
    }

    @PostMapping
    public ApiResponse<String> addPostCode(@RequestBody @Validated AddPostcodeRequest req) {
        return this.postcodeService.addPostcode(req);
    }

    @GetMapping("{postcode}")
    public ApiResponse<Location> getPostcode(@PathVariable(name = "postcode") String postcode) {
        return this.postcodeService.getPostcode(postcode);
    }

    @PostMapping("cal-distance")
    public ApiResponse<CalDistanceResponse> calculateDistance(@Validated @RequestBody CalDistanceRequest req) {
        return this.postcodeService.calDistance(req);
    }
}
