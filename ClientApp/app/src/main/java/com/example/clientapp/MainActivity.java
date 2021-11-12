package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.clientapp.FootballEvent.DashboardActivity;
import com.example.clientapp.Auth.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.RegisterButton).setOnClickListener(v -> startActivity(new Intent(
                MainActivity.this, DashboardActivity.class)));

        findViewById(R.id.LoginGoogleButton).setOnClickListener(v -> startActivity(new Intent(
                MainActivity.this, LoginActivity.class)));
    }
}