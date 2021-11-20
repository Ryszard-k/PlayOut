package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clientapp.FootballEvent.APIClient;
import com.example.clientapp.FootballEvent.FootballEventAPI;
import com.example.clientapp.FootballEvent.Model.FootballEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            Call<List<FootballEvent>> call = APIClient.createService(FootballEventAPI.class).findAllActiveEvent();
            call.enqueue(new Callback<List<FootballEvent>>() {
                @Override
                public void onResponse(Call<List<FootballEvent>> call, Response<List<FootballEvent>> response) {
                    if (response.isSuccessful()){
                        List<FootballEvent> responseList = response.body();

                        for (FootballEvent f : responseList){
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(f.getLatitude(), f.getLongitude()))
                            .title(f.getNote()));
                        }
                        int listSize = responseList.size() - 1;
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(responseList.get(listSize).getLatitude(),
                                responseList.get(listSize).getLongitude())));
                    }
                }

                @Override
                public void onFailure(Call<List<FootballEvent>> call, Throwable t) {
                    Log.d("googleMap", t.getMessage());
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}