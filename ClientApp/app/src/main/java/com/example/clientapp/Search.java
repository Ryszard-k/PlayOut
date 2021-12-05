package com.example.clientapp;

import static com.example.clientapp.auth.Prefs.MyPREFERENCES;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.clientapp.basketball.Basketball;
import com.example.clientapp.basketball.BasketballAPI;
import com.example.clientapp.footballEvent.APIClient;
import com.example.clientapp.footballEvent.FootballEventAPI;
import com.example.clientapp.footballEvent.GoogleMapInfoWindowAdapter;
import com.example.clientapp.footballEvent.model.EventLevel;
import com.example.clientapp.footballEvent.model.FootballEvent;
import com.example.clientapp.volleyball.Volleyball;
import com.example.clientapp.volleyball.VolleyballAPI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            filterEventResultList = new ArrayList<>();

            String[] eventList = {"Football", "Basketball", "Volleyball"};
            boolean[] checkedEventItems = new boolean[eventList.length];
            List<String> selectedEventItems = Arrays.asList(eventList);

            builder.setMultiChoiceItems(eventList, checkedEventItems, (dialog, which, isChecked) ->{
                checkedEventItems[which] = isChecked;
            });

            builder.setPositiveButton(R.string.ok, (dialog, id) -> {
                for (int i = 0; i < checkedEventItems.length; i++) {
                    if (checkedEventItems[i]) {
                        filterEventResultList.add(selectedEventItems.get(i));
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
            filterLevelResultList = new ArrayList<>();

            String[] lvlList = EventLevel.enumToStringArray();
            boolean[] checkedItems = new boolean[lvlList.length];
            List<String> selectedItems = Arrays.asList(lvlList);

            builder.setMultiChoiceItems(lvlList, checkedItems, (dialog, which, isChecked) -> checkedItems[which] = isChecked);

            builder.setPositiveButton(R.string.ok, (dialog, id) -> {
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
                LatLng latLng = com.example.clientapp.Geocoder.latLngFromName(getContext(), location);

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

        Call<EventsWrapper> call = APIClient.createService(EventAPI.class, "Piotr", "piotr123").findAllActiveEvent();
        call.enqueue(new Callback<EventsWrapper>() {
            @Override
            public void onResponse(Call<EventsWrapper> call, Response<EventsWrapper> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<FootballEvent> responseListFootball = response.body().getEventsWrapperWithFootball();
                    List<Basketball> responseListBasketball = response.body().getEventsWrapperWithBasketball();
                    List<Volleyball> responseListVolleyball = response.body().getEventsWrapperWithVolleyball();

                    googleMap.clear();
                    if (filterEventResultList.isEmpty() && filterLevelResultList.isEmpty()) {
                        for (FootballEvent f : responseListFootball) {
                            markersSetUpFootball(f, googleMap);
                        }

                        for (Basketball b : responseListBasketball){
                            markersSetUpBasketball(b, googleMap);
                        }

                        for (Volleyball v : responseListVolleyball){
                            markersSetUpVolleyball(v, googleMap);
                        }
                    } else if (!filterEventResultList.isEmpty() && filterLevelResultList.isEmpty()){
                        for (String e : filterEventResultList) {
                            switch (e) {
                                case "Football":
                                        for (FootballEvent f : responseListFootball) {
                                            markersSetUpFootball(f, googleMap);
                                    }
                                    break;

                                case "Basketball":
                                    for (Basketball b : responseListBasketball){
                                        markersSetUpBasketball(b, googleMap);
                                    }
                                    break;

                                case "Volleyball":
                                    for (Volleyball v : responseListVolleyball){
                                        markersSetUpVolleyball(v, googleMap);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    } else if (filterEventResultList.isEmpty() && !filterLevelResultList.isEmpty()) {
                        for (FootballEvent f : responseListFootball) {
                            if (filterLevelResultList.contains(f.getEventLevel().name())){
                                markersSetUpFootball(f, googleMap);
                            }
                        }

                        for (Basketball b : responseListBasketball){
                            if (filterLevelResultList.contains(b.getEventLevel().name())) {
                                markersSetUpBasketball(b, googleMap);
                            }
                        }

                        for (Volleyball v : responseListVolleyball){
                            if (filterLevelResultList.contains(v.getEventLevel().name())) {
                                markersSetUpVolleyball(v, googleMap);
                            }
                        }

                    } else if (!filterEventResultList.isEmpty() && !filterLevelResultList.isEmpty()) {
                        for (String e : filterEventResultList) {
                            switch (e) {
                                case "Football":
                                    for (FootballEvent f : responseListFootball) {
                                        if (filterLevelResultList.contains(f.getEventLevel().name())){
                                            markersSetUpFootball(f, googleMap);
                                        }
                                    }
                                    break;

                                case "Basketball":
                                    for (Basketball b : responseListBasketball){
                                        if (filterLevelResultList.contains(b.getEventLevel().name())) {
                                            markersSetUpBasketball(b, googleMap);
                                        }
                                    }
                                    break;

                                case "Volleyball":
                                    for (Volleyball v : responseListVolleyball){
                                        if (filterLevelResultList.contains(v.getEventLevel().name())) {
                                            markersSetUpVolleyball(v, googleMap);
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

               /*     Locale defaultLocale = Resources.getSystem().getConfiguration().getLocales().get(0);
                    LatLng latLng = Geocoder.latLngFromName(getContext(), defaultLocale.getDisplayName());
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));*/

                }
            }

            @Override
            public void onFailure(Call<EventsWrapper> call, Throwable t) {
                Log.d("googleMap", Log.getStackTraceString(t));
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
                    startActivity(new Intent(getContext(), AddBasketballEvent.class)
                            .putExtra("latitude", latLng.latitude).putExtra("longitude", latLng.longitude));
                    break;
                case 1:
                    startActivity(new Intent(getContext(), com.example.clientapp.basketball.AddBasketballEvent.class)
                            .putExtra("latitude", latLng.latitude).putExtra("longitude", latLng.longitude));
                    break;
                case 2:
                    startActivity(new Intent(getContext(), com.example.clientapp.volleyball.AddVolleyballEvent.class)
                            .putExtra("latitude", latLng.latitude).putExtra("longitude", latLng.longitude));
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

                                getActivity().getApplicationContext().stopService(new Intent(getActivity(), DashboardActivity.class));
                                startActivity(new Intent(getActivity(), DashboardActivity.class));

                                getActivity().getApplicationContext().stopService(new Intent(getActivity(), DashboardActivity.class));
                                startActivity(new Intent(getActivity(), DashboardActivity.class));

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
                    Call<Void> call1 = APIClient.createService(BasketballAPI.class).joinToEvent(eventId, "Piotr"); //sharedpreferences.getString(Username, Username)
                    call1.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(getContext(), "Joined to event", Toast.LENGTH_LONG).show();

                                getActivity().getApplicationContext().stopService(new Intent(getActivity(), DashboardActivity.class));
                                startActivity(new Intent(getActivity(), DashboardActivity.class));

                            } else if (response.raw().code() == HttpsURLConnection.HTTP_CONFLICT){
                                Toast.makeText(getContext(), "You are already attending the event", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("joinBasketball", Log.getStackTraceString(t));
                        }
                    });
                    break;

                case "Volleyball":
                    Call<Void> call2 = APIClient.createService(VolleyballAPI.class).joinToEvent(eventId, "Piotr"); //sharedpreferences.getString(Username, Username)
                    call2.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(getContext(), "Joined to event", Toast.LENGTH_LONG).show();

                                getActivity().getApplicationContext().stopService(new Intent(getActivity(), DashboardActivity.class));
                                startActivity(new Intent(getActivity(), DashboardActivity.class));

                            } else if (response.raw().code() == HttpsURLConnection.HTTP_CONFLICT){
                                Toast.makeText(getContext(), "You are already attending the event", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("joinBasketball", Log.getStackTraceString(t));
                        }
                    });
                    break;
                default: break;
            }

            dialog.dismiss();
        });

        builder.setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void markersSetUpFootball(FootballEvent f, GoogleMap map) {
        String snippet = "Date: " + f.getDate() + "\n" +
                "Time: " + f.getTime() + "\n" +
                "Address: " + f.getLocation() + "\n" +
                "Level: " + f.getEventLevel() + "\n" +
                "Vacancies: " + f.getVacancies() + "\n" +
                "Author: " + f.getAuthor().getUsername() + "\n" +
                "Note: " + f.getNote() + "\n";
        map.setInfoWindowAdapter(new GoogleMapInfoWindowAdapter(getContext()));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(f.getLatitude(), f.getLongitude()))
                .title("Football Event " + f.getId())
                .snippet(snippet))
                .setIcon(BitmapDescriptorFactory.defaultMarker(359f));
    }

    private void markersSetUpBasketball(Basketball f, GoogleMap map) {
        String snippet = "Date: " + f.getDate() + "\n" +
                "Time: " + f.getTime() + "\n" +
                "Address: " + f.getLocation() + "\n" +
                "Level: " + f.getEventLevel() + "\n" +
                "Vacancies: " + f.getVacancies() + "\n" +
                "Author: " + f.getAuthorBasketball().getUsername() + "\n" +
                "Note: " + f.getNote() + "\n";
        map.setInfoWindowAdapter(new GoogleMapInfoWindowAdapter(getContext()));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(f.getLatitude(), f.getLongitude()))
                .title("Football Event " + f.getId())
                .snippet(snippet))
        .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

    }

    private void markersSetUpVolleyball(Volleyball f, GoogleMap map) {
        String snippet = "Date: " + f.getDate() + "\n" +
                "Time: " + f.getTime() + "\n" +
                "Address: " + f.getLocation() + "\n" +
                "Level: " + f.getEventLevel() + "\n" +
                "Vacancies: " + f.getVacancies() + "\n" +
                "Author: " + f.getAuthorVolleyball().getUsername() + "\n" +
                "Note: " + f.getNote() + "\n";
        map.setInfoWindowAdapter(new GoogleMapInfoWindowAdapter(getContext()));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(f.getLatitude(), f.getLongitude()))
                .title("Football Event " + f.getId())
                .snippet(snippet))
                .setIcon(BitmapDescriptorFactory.defaultMarker(250f));
    }
}