package com.example.distancecounter.v1.counter;

import com.example.distancecounter.common.bean.ApiResponse;
import com.example.distancecounter.common.models.Location;
import com.example.distancecounter.v1.counter.dtos.req.AddPostcodeRequest;
import com.example.distancecounter.v1.counter.dtos.req.CalDistanceRequest;
import com.example.distancecounter.v1.counter.dtos.res.CalDistanceResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostcodeServiceImpl implements PostcodeService {
    private final static double EARTH_RADIUS = 6371;

    PostcodeRepository postcodeRepository;
    public PostcodeServiceImpl(PostcodeRepository postcodeRepository) {
        this.postcodeRepository = postcodeRepository;
    }

    @Override
    public ApiResponse<Location> getPostcode(String postcode) {
        Location location = this.postcodeRepository.getLocationByPostcode(postcode);
        if(location == null ) {
            throw new RuntimeException("Not found postcode");
        }
        return ApiResponse.success("Get Postcode Successfully!", location);
    }

    @Override
    public ApiResponse<String> addPostcode(AddPostcodeRequest req) {
        int result = this.postcodeRepository.addLocation(req);
        if (result != 1) {
            throw new RuntimeException("Insert Postcode failed");
        }
        return ApiResponse.success("Added Postcode Successfully!");
    }

    @Override
    public ApiResponse<CalDistanceResponse> calDistance(CalDistanceRequest req) {
        Location pointA = this.postcodeRepository.getLocationByPostcode(req.getPostcodeA());
        Location pointB = this.postcodeRepository.getLocationByPostcode(req.getPostcodeB());

        if(pointA == null || pointB == null) {
            throw new RuntimeException("Not found pointA or pointB");
        }

        double totalDistance = calculateDistance(pointA.getLatitude(), pointA.getLongitude(), pointB.getLatitude(), pointB.getLongitude());

        return ApiResponse.success("Calculated Distance successfully!",
            new CalDistanceResponse(
                List.of(
                    pointA,
                    pointB
                ),
                totalDistance
            )
        );
    }

    private double calculateDistance(double latitude, double longitude, double latitude2, double longitude2) {
        double lon1Radians = Math.toRadians(longitude);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude);
        double lat2Radians = Math.toRadians(latitude2);
        double a = haversine(lat1Radians, lat2Radians) + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTH_RADIUS * c);
    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }
    private double square(double x) {
        return x * x;
    }
}
