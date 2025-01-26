package com.example.distancecounter.common.bean;

import com.example.distancecounter.common.models.Location;
import com.example.distancecounter.v1.counter.dtos.res.CalDistanceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean status;
    private String msg;
    private T data;

    public static <T> ApiResponse<T> success(String msg, T data) {
        return ApiResponse.<T>builder().status(true).msg(msg).data(data).build();
    }

    public static <T> ApiResponse<T> success(String msg) {
        return ApiResponse.<T>builder().status(true).msg(msg).build();
    }


    public static <T> ApiResponse<T> fail(String msg) {
        return ApiResponse.<T>builder().status(false).msg(msg).build();
    }
}
