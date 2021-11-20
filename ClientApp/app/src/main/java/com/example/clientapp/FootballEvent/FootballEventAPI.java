package com.example.clientapp.FootballEvent;

import com.example.clientapp.FootballEvent.Model.AppUser;
import com.example.clientapp.FootballEvent.Model.FootballEvent;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FootballEventAPI {

    String BASE_URL = "http://10.0.2.2:8080";

    @GET("/footballEvent")
    @Headers("Content-Type: application/json")
    Call<List<FootballEvent>> getAllEvent();


    @GET("/footballEvent/activeEvent/all")
    @Headers("Content-Type: application/json")
    Call<List<FootballEvent>> findAllActiveEvent();

    @GET("/footballEvent/activeEvent/{username}")
    @Headers("Content-Type: application/json")
    Call<List<FootballEvent>> getMyActiveEvent(@Path("username") String username);

    @GET("/footballEvent/historyEvent/{username}")
    @Headers("Content-Type: application/json")
    Call<List<FootballEvent>> getMyHistoryEvent(@Path("username") String username);

    @GET("/footballEvent/{id}")
    Call<List<FootballEvent>> getByIdEvent();

    @POST("/footballEvent")
    Call<Void> addEvent(@Body FootballEvent footballEvent);

    @DELETE("/footballEvent/{id}")
    Call<Void> deleteEvent(@Path("id") Long id);
}
