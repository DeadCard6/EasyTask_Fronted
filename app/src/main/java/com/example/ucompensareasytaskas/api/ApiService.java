package com.example.ucompensareasytaskas.api;

import com.example.ucompensareasytaskas.api.model.ApiResponse;
import com.example.ucompensareasytaskas.api.model.LoginRequest;
import com.example.ucompensareasytaskas.api.model.Note;
import com.example.ucompensareasytaskas.api.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/api/users/register")
    Call<ApiResponse> registerUser(@Body User user);

    @POST("/api/users/login")
    Call<ApiResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("/api/notes/user/{userId}")
    Call<Note> createNote(@Path("userId") long userId, @Body Note note);

    @GET("/api/notes/user/{userId}")
    Call<List<Note>> getNotesByUser(@Path("userId") Long userId);

}
