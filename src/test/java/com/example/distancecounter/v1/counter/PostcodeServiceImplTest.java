package com.example.distancecounter.v1.counter;

import com.example.distancecounter.common.bean.ApiResponse;
import com.example.distancecounter.common.models.Location;
import com.example.distancecounter.v1.counter.dtos.req.AddPostcodeRequest;
import com.example.distancecounter.v1.counter.dtos.req.CalDistanceRequest;
import com.example.distancecounter.v1.counter.dtos.res.CalDistanceResponse;
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
class PostcodeServiceImplTest {

    @Mock
    private PostcodeRepository postcodeRepository;

    @InjectMocks
    private PostcodeServiceImpl postcodeService;

    @Test
    void getPostcode() {
        String postcode = "TEST 001";
        double latitude = 12.34;
        double longitude = 56.78;

        Location mockPostcode = new Location(postcode, latitude, longitude);
        when(postcodeRepository.getLocationByPostcode(postcode)).thenReturn(mockPostcode); // Mock repository method

        ApiResponse<Location> response = postcodeService.getPostcode(postcode);

        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals("Get Postcode Successfully!", response.getMsg());
        assertEquals(postcode, response.getData().getPostcode());
        assertEquals(latitude, response.getData().getLatitude());
        assertEquals(longitude, response.getData().getLongitude());

        verify(postcodeRepository).getLocationByPostcode(postcode);
    }

    @Test
    void addPostcode() {
        AddPostcodeRequest request = new AddPostcodeRequest();
        request.setPostcode("TEST 001");
        request.setLatitude(12.34);
        request.setLongitude(56.78);

        when(postcodeRepository.addLocation(request)).thenReturn(1);

        ApiResponse<String> response = postcodeService.addPostcode(request);

        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals("Added Postcode Successfully!", response.getMsg());

        verify(postcodeRepository).addLocation(any());
    }

    @Test
    void calDistance() {
        CalDistanceRequest req = new CalDistanceRequest();
        req.setPostcodeA("TEST 001");
        req.setPostcodeB("TEST 002");

        Location mockPointA = new Location("TEST 001", 12.34, 56.78);
        Location mockPointB = new Location("TEST 002", 12.34, 56.78);

        double mockDistance = 0.0;

        when(postcodeRepository.getLocationByPostcode("TEST 001")).thenReturn(mockPointA);
        when(postcodeRepository.getLocationByPostcode("TEST 002")).thenReturn(mockPointB);

        // Act
        ApiResponse<CalDistanceResponse> response = postcodeService.calDistance(req);

        // Assert
        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals("Calculated Distance successfully!", response.getMsg());
        assertEquals(mockDistance, response.getData().getDistance());
        assertEquals("km", response.getData().getUnit());
        assertEquals(mockPointA, response.getData().getLocations().get(0));
        assertEquals(mockPointB, response.getData().getLocations().get(1));

        verify(postcodeRepository).getLocationByPostcode("TEST 001");
        verify(postcodeRepository).getLocationByPostcode("TEST 002");
    }
}