package com.example.clientapp.Register;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.clientapp.R.layout.login_activity);

        etUsername = findViewById(com.example.clientapp.R.id.etUsername);
        etPassword = findViewById(com.example.clientapp.R.id.etPassword);
    }
}
