package com.example.clientapp.Authentication;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Username = "UsernameKey";
    public static final String Password = "PasswordKey";

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

    public void save(String username, String password){

        editor.putString(Username, username);
        editor.putString(Password, password);
        editor.commit();

    }
}
