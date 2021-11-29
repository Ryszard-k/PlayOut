package com.example.clientapp.FootballEvent;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.clientapp.FootballEvent.Model.EventLevel;
import com.example.clientapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.time.LocalDate;
import java.time.LocalTime;

public class GoogleMapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private EventLevel eventLevel;
    private int vacancies;
    private String note;

    public GoogleMapInfoWindowAdapter(Context context, LocalDate date, LocalTime time, String location, EventLevel eventLevel, int vacancies, String note) {
        this.context = context;
        this.date = date;
        this.time = time;
        this.location = location;
        this.eventLevel = eventLevel;
        this.vacancies = vacancies;
        this.note = note;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.map_custom_infowindow, null);

        TextView dateTextView = view.findViewById(R.id.dateTextView);
        TextView timeTextView = view.findViewById(R.id.timeTextView);
        TextView locationTextView = view.findViewById(R.id.locationTextView);
        TextView lvlTextView = view.findViewById(R.id.lvlTextView);
        TextView vacanciesTextView = view.findViewById(R.id.vacanciesTextView);
        TextView noteTextView = view.findViewById(R.id.noteTextView);
        Button joinButton = view.findViewById(R.id.joinButton);

        dateTextView.setText(date.toString());
        timeTextView.setText(time.toString());
        locationTextView.setText(location);
        lvlTextView.setText(eventLevel.toString());
        vacanciesTextView.setText(vacancies);
        noteTextView.setText(note);

        joinButton.setOnClickListener(v -> {

        });

        return view;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
}
