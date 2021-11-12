package com.example.clientapp.Auth;

import com.example.clientapp.FootballEvent.Model.AppUser;
import com.example.clientapp.FootballEvent.Model.LoginRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/login")
    Call<AppUser> loginUser(@Body LoginRequest loginRequest);

    @GET("/appUser")
    Call<List<AppUser>> getAllAppUsers();
}
