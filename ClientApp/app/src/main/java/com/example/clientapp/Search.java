package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.clientapp.FootballEvent.APIClient;
import com.example.clientapp.FootballEvent.FootballEventAPI;
import com.example.clientapp.FootballEvent.Model.EventLevel;
import com.example.clientapp.FootballEvent.Model.FootballEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends Fragment {

    List<String> filterResultList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button filterButton = view.findViewById(R.id.filterButton);

        Button searchButton = view.findViewById(R.id.searchLocationButton);
        searchButton.setOnClickListener(v -> {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                List<Address> abc = geocoder.getFromLocationName("Rondo mogilskie", 1);
                System.out.println(abc.get(0).getLatitude());
                System.out.println(abc.get(0).getLongitude());
                addresses = geocoder.getFromLocation(50.06109, 19.91220, 1);
                System.out.println(addresses.get(0).getFeatureName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callbackWithFilters);
        }

        filterButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setTitle("Filter");

            String[] lvlList = EventLevel.enumToStringArray();
            boolean[] checkedItems = new boolean[lvlList.length];
            List<String> selectedItems = Arrays.asList(lvlList);

            builder.setMultiChoiceItems(lvlList, checkedItems, (dialog, which, isChecked) -> {
                checkedItems[which] = isChecked;
                // String currentItem = selectedItems.get(which);
            });

            builder.setPositiveButton(R.string.ok, (dialog, id) -> {
                filterResultList = new ArrayList<>();
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) {
                        filterResultList.add(selectedItems.get(i).substring(0, 1));
                    }
                }

                mapFragment.getMapAsync(callbackWithFilters);
            });

            builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
                dialog.cancel();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        });
    }

    private final OnMapReadyCallback callbackWithFilters = googleMap -> {
        Call<List<FootballEvent>> call = APIClient.createService(FootballEventAPI.class).findAllActiveEvent();
        call.enqueue(new Callback<List<FootballEvent>>() {
            @Override
            public void onResponse(Call<List<FootballEvent>> call, Response<List<FootballEvent>> response) {
                if (response.isSuccessful()) {
                    List<FootballEvent> responseList = response.body();
                    googleMap.clear();

                    if (!filterResultList.isEmpty()) {
                        for (FootballEvent f : responseList) {
                            if (filterResultList.contains(f.getEventLevel().name()))
                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(f.getLatitude(), f.getLongitude()))
                                        .title(f.getNote()));
                        }
                        int listSize = responseList.size() - 1;
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(responseList.get(listSize).getLatitude(),
                                responseList.get(listSize).getLongitude()), 1));
                    } else {
                        for (FootballEvent f : responseList){
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(f.getLatitude(), f.getLongitude()))
                                    .title(f.getNote()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FootballEvent>> call, Throwable t) {
                Log.d("googleMap", t.getMessage());
            }
        });
    };
}