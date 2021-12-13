package com.example.clientapp.Football;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.clientapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class GoogleMapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private View view;

    public GoogleMapInfoWindowAdapter(Context context) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.map_custom_infowindow, null);
    }

    private void renderText(Marker marker, View view){
        TextView dateTextView = view.findViewById(R.id.dateTextViewDetails);

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
