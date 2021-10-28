package com.example.clientapp.FootballEvent;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class APIClient {

    private static APIClient instance = null;
    private final FootballEventAPI footballEventAPI;

    private APIClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FootballEventAPI.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        footballEventAPI = retrofit.create(FootballEventAPI.class);
    }

    public static synchronized APIClient getInstance() {
        if (instance == null) {
            instance = new APIClient();
        }
        return instance;
    }

    public FootballEventAPI getMyApi() {
        return footballEventAPI;
    }

}
