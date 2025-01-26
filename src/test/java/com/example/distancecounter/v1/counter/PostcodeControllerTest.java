package com.example.distancecounter.v1.counter;

import com.example.distancecounter.common.bean.ApiResponse;
import com.example.distancecounter.common.models.Location;
import com.example.distancecounter.v1.counter.dtos.req.AddPostcodeRequest;
import com.example.distancecounter.v1.counter.dtos.req.CalDistanceRequest;
import com.example.distancecounter.v1.counter.dtos.res.CalDistanceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostcodeController.class)
class PostcodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostcodeService postcodeService;

    @Test
    @Order(1)
    void addPostCode() throws Exception {
        AddPostcodeRequest req = new AddPostcodeRequest();
        req.setPostcode("TEST 001");
        req.setLatitude(12.34);
        req.setLongitude(56.78);

        ApiResponse<String> mockRes = ApiResponse.success("Postcode added successfully");
        when(postcodeService.addPostcode(req)).thenReturn(mockRes);

        mockMvc.perform(post("/v1/postcode")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(true))
            .andExpect(jsonPath("$.msg").value("Postcode added successfully"));

        verify(postcodeService).addPostcode(req);
    }

    @Test
    @Order(2)
    void addPostCode_invalid() throws Exception {
        AddPostcodeRequest invalidRequest = new AddPostcodeRequest();
        invalidRequest.setPostcode("");
        invalidRequest.setLatitude(12.34);
        invalidRequest.setLongitude(56.78);

        mockMvc.perform(post("/v1/postcode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(false))
            .andExpect(jsonPath("$.msg").value("Error - postcode is required"));
    }


    @Test
    @Order(3)
    void getPostcode() throws Exception {
        String postcode = "TEST 001";
        Location mockLocation = new Location("TEST 001", 12.34, 56.78);
        ApiResponse<Location> mockRes = ApiResponse.success("Get Postcode Successfully!", mockLocation);

        when(postcodeService.getPostcode(postcode)).thenReturn(mockRes);

        mockMvc.perform(get("/v1/postcode/{postcode}", postcode))
            .andExpect(status().isOk()) // Expecting 200 OK status
            .andExpect(jsonPath("$.status").value(true))
            .andExpect(jsonPath("$.msg").value("Get Postcode Successfully!"))
            .andExpect(jsonPath("$.data.postcode").value(mockLocation.getPostcode()))
            .andExpect(jsonPath("$.data.latitude").value(mockLocation.getLatitude()))
            .andExpect(jsonPath("$.data.longitude").value(mockLocation.getLongitude()));

        verify(postcodeService).getPostcode(postcode);
    }

    @Test
    @Order(4)
    void calculateDistance() throws Exception {
        CalDistanceRequest req = new CalDistanceRequest();
        req.setPostcodeA("TEST 001");
        req.setPostcodeB("TEST 002");

        Location mockPointA = new Location("TEST 001", 12.34, 56.78);
        Location mockPointB = new Location("TEST 002", 12.34, 56.78);
        ApiResponse<CalDistanceResponse> mockRes = ApiResponse.success(
            "Calculated Distance successfully!",
            new CalDistanceResponse(
                List.of(
                    mockPointA,
                    mockPointB
                ),
                0.0
            )
        );

        when(postcodeService.calDistance(req)).thenReturn(mockRes);

        mockMvc.perform(post("/v1/postcode/cal-distance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(true))
            .andExpect(jsonPath("$.msg").value("Calculated Distance successfully!"))
            .andExpect(jsonPath("$.data.locations[0].postcode").value("TEST 001"))
            .andExpect(jsonPath("$.data.locations[0].latitude").value(12.34))
            .andExpect(jsonPath("$.data.locations[0].longitude").value(56.78))
            .andExpect(jsonPath("$.data.locations[1].postcode").value("TEST 002"))
            .andExpect(jsonPath("$.data.locations[1].latitude").value(12.34))
            .andExpect(jsonPath("$.data.locations[1].longitude").value(56.78))
            .andExpect(jsonPath("$.data.distance").value("0.0"))
            .andExpect(jsonPath("$.data.unit").value("km"));

        verify(postcodeService).calDistance(req);
    }
}