package com.example.clientapp;

import android.content.Context;
import android.location.Address;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Geocoder {

    public static String nameFromLatLon(Context context, double latitude, double longitude) {
        android.location.Geocoder geocoder = new android.location.Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            return addresses.get(0).getFeatureName();
        } catch (IOException e) {
            Log.d("Geocoder", e.getMessage());
            return null;
        }
    }

    public static LatLng latLngFromName(Context context, String name){
        android.location.Geocoder geocoder = new android.location.Geocoder(context, Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(name, 1);
            return new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String countryNameFromLatLon(Context context, double latitude, double longitude) {
        android.location.Geocoder geocoder = new android.location.Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            return addresses.get(0).getCountryName();
        } catch (IOException e) {
            Log.d("Geocoder", e.getMessage());
            return null;
        }
    }
}
