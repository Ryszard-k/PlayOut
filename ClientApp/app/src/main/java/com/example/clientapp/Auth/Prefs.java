package com.example.clientapp.Auth;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Username = "UsernameKey";
    public static final String Password = "PasswordKey";
    public static final String Id = "IdKey";

    private static Prefs instance;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    private Prefs(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public static Prefs getInstance(Context context) {
        return instance == null ? new Prefs(context) : instance;
    }

    public void save(String username, String password, Long id){

        editor.putString(Username, username);
        editor.putString(Password, password);
        editor.putLong(Id, id);
        editor.commit();

    }

    public String getUsername() {
        return sharedpreferences.getString(Username, "brak username");
    }

    public static String getPassword() {
        return Password;
    }

    public static String getId() {
        return Id;
    }
}
