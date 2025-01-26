package com.example.distancecounter.v1.counter;

import com.example.distancecounter.common.bean.ApiResponse;
import com.example.distancecounter.common.models.Location;
import com.example.distancecounter.v1.counter.dtos.req.AddLocationRequest;
import com.example.distancecounter.v1.counter.dtos.req.CalDistanceRequest;
import com.example.distancecounter.v1.counter.dtos.res.CalDistanceResponse;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    @Test
    @Order(1)
    void getLocation() {
        String postcode = "TEST 001";
        double latitude = 12.34;
        double longitude = 56.78;

        Location mockPostcode = new Location(postcode, latitude, longitude);
        when(locationRepository.getLocationByPostcode(postcode)).thenReturn(mockPostcode); // Mock repository method

        ApiResponse<Location> response = locationService.getLocation(postcode);

        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals("Get Postcode Successfully!", response.getMsg());
        assertEquals(postcode, response.getData().getPostcode());
        assertEquals(latitude, response.getData().getLatitude());
        assertEquals(longitude, response.getData().getLongitude());

        verify(locationRepository).getLocationByPostcode(postcode);
    }

    @Test
    @Order(2)
    void addLocation() {
        AddLocationRequest request = new AddLocationRequest();
        request.setPostcode("TEST 001");
        request.setLatitude(12.34);
        request.setLongitude(56.78);

        when(locationRepository.addLocation(request)).thenReturn(1);

        ApiResponse<String> response = locationService.addLocation(request);

        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals("Added Postcode Successfully!", response.getMsg());

        verify(locationRepository).addLocation(any());
    }

    @Test
    @Order(3)
    void calDistance() {
        CalDistanceRequest req = new CalDistanceRequest();
        req.setPostcodeA("TEST 001");
        req.setPostcodeB("TEST 002");

        Location mockPointA = new Location("TEST 001", 12.34, 56.78);
        Location mockPointB = new Location("TEST 002", 12.34, 56.78);

        double mockDistance = 0.0;

        when(locationRepository.getLocationByPostcode("TEST 001")).thenReturn(mockPointA);
        when(locationRepository.getLocationByPostcode("TEST 002")).thenReturn(mockPointB);

        ApiResponse<CalDistanceResponse> response = locationService.calDistance(req);

        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals("Calculated Distance successfully!", response.getMsg());
        assertEquals(mockDistance, response.getData().getDistance());
        assertEquals("km", response.getData().getUnit());
        assertEquals(mockPointA, response.getData().getLocations().get(0));
        assertEquals(mockPointB, response.getData().getLocations().get(1));

        verify(locationRepository).getLocationByPostcode("TEST 001");
        verify(locationRepository).getLocationByPostcode("TEST 002");
    }
}