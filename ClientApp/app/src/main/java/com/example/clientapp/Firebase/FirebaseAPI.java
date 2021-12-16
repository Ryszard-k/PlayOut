package com.example.clientapp.Firebase;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface FirebaseAPI {

    @PATCH("/notification/{username}")
    @Headers("Content-Type: application/json")
    Call<Void> saveToken(@Body String token, @Path("username") String username);
}
