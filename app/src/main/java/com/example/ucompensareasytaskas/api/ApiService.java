package com.example.ucompensareasytaskas.api;

import com.example.ucompensareasytaskas.api.model.ApiResponse;
import com.example.ucompensareasytaskas.api.model.LoginRequest;
import com.example.ucompensareasytaskas.api.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/register")
    Call<ApiResponse> registerUser(@Body User user);

    @POST("/api/users/login")
    Call<ApiResponse> loginUser(@Body LoginRequest loginRequest);
}
