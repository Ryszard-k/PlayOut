package com.example.clientapp.auth;

import com.example.clientapp.Football.Model.AppUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthService {

    @GET("/appUser")
    Call<List<AppUser>> getAllAppUsers();

    @GET("/appUser/username/{username}")
    @Headers("Content-Type: application/json")
    Call<String> checkAppUser(@Path("username") String username);

    @POST("/appUser")
    Call<String> register(@Body AppUser appUser);
}
