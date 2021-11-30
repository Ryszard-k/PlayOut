package com.example.clientapp.FootballEvent;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.clientapp.FootballEvent.Model.EventLevel;
import com.example.clientapp.FootballEvent.Model.FootballEvent;
import com.example.clientapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.time.LocalDate;
import java.time.LocalTime;

public class GoogleMapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private View view;

    public GoogleMapInfoWindowAdapter(Context context) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.map_custom_infowindow, null);
    }

    private void renderText(Marker marker, View view){
        TextView dateTextView = view.findViewById(R.id.dateTextView);
  /*      TextView timeTextView = view.findViewById(R.id.timeTextView);
        TextView locationTextView = view.findViewById(R.id.locationTextView);
        TextView lvlTextView = view.findViewById(R.id.lvlTextView);
        TextView vacanciesTextView = view.findViewById(R.id.vacanciesTextView);
        TextView noteTextView = view.findViewById(R.id.noteTextView);
        //     Button joinButton = view.findViewById(R.id.joinButton);
/*
        dateTextView.setText(footballEvent.getDate().toString());
        timeTextView.setText(footballEvent.getTime().toString());
        locationTextView.setText(footballEvent.getLocation());
        lvlTextView.setText(footballEvent.getEventLevel().toString());
        vacanciesTextView.setText(Integer.toString(footballEvent.getVacancies()));
        noteTextView.setText(footballEvent.getNote());
*/
        dateTextView.setText(marker.getSnippet());
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        renderText(marker, view);
        return view;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        renderText(marker, view);
        return view;
    }
}
