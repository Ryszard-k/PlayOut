package com.example.clientapp;

import static com.example.clientapp.Auth.Prefs.MyPREFERENCES;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.clientapp.Basketball.Basketball;
import com.example.clientapp.FootballEvent.APIClient;
import com.example.clientapp.FootballEvent.FootballEventAPI;
import com.example.clientapp.FootballEvent.GoogleMapInfoWindowAdapter;
import com.example.clientapp.FootballEvent.Model.EventLevel;
import com.example.clientapp.FootballEvent.Model.FootballEvent;
import com.example.clientapp.Volleyball.Volleyball;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class Search extends Fragment implements GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowClickListener {

    List<String> filterLevelResultList = new ArrayList<>();
    List<String> filterEventResultList = new ArrayList<>();
    private SearchView searchView;
    private GoogleMap map;

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
        Button filterLevelButton = view.findViewById(R.id.filterLevelButton);
        Button filterEventButton = view.findViewById(R.id.filterEventButton);
        searchView = view.findViewById(R.id.idSearchView);

        SharedPreferences sharedpreferences = view.getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callbackWithFilters);
        }

        filterEventButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setTitle("Event filter");

            String[] eventList = {"Football", "Basketball", "Volleyball"};
            boolean[] checkedEventItems = new boolean[eventList.length];
            List<String> selectedEventItems = Arrays.asList(eventList);

            builder.setMultiChoiceItems(eventList, checkedEventItems, (dialog, which, isChecked) ->{
                checkedEventItems[which] = isChecked;
            });

            builder.setPositiveButton(R.string.ok, (dialog, id) -> {
                filterEventResultList = new ArrayList<>();
                for (int i = 0; i < checkedEventItems.length; i++) {
                    if (checkedEventItems[i]) {
                        filterEventResultList.add(selectedEventItems.get(i).substring(0, 1));
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

        filterLevelButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setTitle("Level filter");

            String[] lvlList = EventLevel.enumToStringArray();
            boolean[] checkedItems = new boolean[lvlList.length];
            List<String> selectedItems = Arrays.asList(lvlList);

            builder.setMultiChoiceItems(lvlList, checkedItems, (dialog, which, isChecked) -> checkedItems[which] = isChecked);

            builder.setPositiveButton(R.string.ok, (dialog, id) -> {
                filterLevelResultList = new ArrayList<>();
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) {
                        filterLevelResultList.add(selectedItems.get(i).substring(0, 1));
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                Geocoder geocoder = new Geocoder(getContext());
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final OnMapReadyCallback callbackWithFilters = googleMap -> {
        map = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);

        Call<EventsWrapper> call = APIClient.createService(EventAPI.class).findAllActiveEvent();
        call.enqueue(new Callback<EventsWrapper>() {
            @Override
            public void onResponse(Call<EventsWrapper> call, Response<EventsWrapper> response) {
                if (response.isSuccessful()) {
                    List<FootballEvent> responseListFootball = response.body().getEventsWrapperWithFootball();
                    List<Basketball> responseListBasketball = response.body().getEventsWrapperWithBasketball();
                    List<Volleyball> responseListVolleyball = response.body().getEventsWrapperWithVolleyball();

                    googleMap.clear();
/*
                    if (!filterEventResultList.isEmpty() && !filterLevelResultList.isEmpty()){
                        for ()
                    } else if (filterEventResultList.isEmpty() && !filterLevelResultList.isEmpty()){

                    } else if (!filterEventResultList.isEmpty() && filterLevelResultList.isEmpty()){

                    } else {

                    }*/

                    if (!filterLevelResultList.isEmpty()) {
                        for (FootballEvent f : responseListFootball) {
                            if (filterLevelResultList.contains(f.getEventLevel().name()))
                                googleMap.setInfoWindowAdapter(new GoogleMapInfoWindowAdapter(getContext()));

                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(f.getLatitude(), f.getLongitude())));
                        }
                        int listSize = responseListFootball.size() - 1;
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(responseListFootball.get(listSize).getLatitude(),
                                responseListFootball.get(listSize).getLongitude()), 1));
                    } else {
                        for (FootballEvent f : responseListFootball){
                            String snippet = "Date: " + f.getDate() + "\n" +
                                    "Time: " + f.getTime() + "\n" +
                                    "Address: " + f.getLocation() + "\n" +
                                    "Level: " + f.getEventLevel() + "\n" +
                                    "Vacancies: " + f.getVacancies() + "\n" +
                                    "Note: " + f.getNote() + "\n";
                            googleMap.setInfoWindowAdapter(new GoogleMapInfoWindowAdapter(getContext()));
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(f.getLatitude(), f.getLongitude()))
                                    .title("Football Event " +f.getId())
                            .snippet(snippet));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<EventsWrapper> call, Throwable t) {
                Log.d("googleMap", t.getMessage());
            }
        });
    };

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        String[] lvlList = {"Football", "Basketball", "Volleyball"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create event");

        builder.setSingleChoiceItems(lvlList, -1, (dialog, which) -> {
            switch (which) {
                case 0:
                    startActivity(new Intent(getContext(), AddFootballEventActivity.class)
                            .putExtra("latitude", latLng.latitude).putExtra("longitude", latLng.longitude));
                    break;
                case 1:

                    break;
                case 2:

                    break;
                default:
                    Toast.makeText(getContext(), "Please choose type of event", Toast.LENGTH_LONG).show();
                    break;
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure you want to join the event?");
        String markerTitle = marker.getTitle();
        String a = Objects.requireNonNull(markerTitle).substring(0, markerTitle.indexOf(" "));
        Long eventId = Long.valueOf(markerTitle.substring(markerTitle.lastIndexOf(" ", 15) + 1));

        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            switch (a){
                case "Football":
                    Call<Void> call = APIClient.createService(FootballEventAPI.class).joinToEvent(eventId, "Piotr"); //sharedpreferences.getString(Username, Username)
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(getContext(), "Joined to event", Toast.LENGTH_LONG).show();
                            } else if (response.raw().code() == HttpsURLConnection.HTTP_CONFLICT){
                                Toast.makeText(getContext(), "You are already attending the event", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("joinFootball", Log.getStackTraceString(t));
                        }
                    });
                    break;

                case "Basketball":

                    break;

                case "Volleyball":

                    break;
                default: break;
            }
            dialog.dismiss();
        });

        builder.setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}