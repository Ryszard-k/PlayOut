package com.example.clientapp;

import static com.example.clientapp.Auth.Prefs.MyPREFERENCES;
import static com.example.clientapp.Auth.Prefs.Password;
import static com.example.clientapp.Auth.Prefs.Username;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.clientapp.Auth.AuthService;
import com.example.clientapp.Auth.RegisterActivity;
import com.example.clientapp.Auth.LoginActivity;
import com.example.clientapp.FootballEvent.APIClient;
import com.example.clientapp.FootballEvent.DashboardActivity2;
import com.example.clientapp.FootballEvent.Model.AppUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        findViewById(R.id.LoginGoogleButton).setOnClickListener(v -> {
            if (sharedpreferences.contains(Username) && sharedpreferences.contains(Password)){
                String username = sharedpreferences.getString(Username, Username);
                String password = sharedpreferences.getString(Password, Password);

                Call<List<AppUser>> call = APIClient.createService(AuthService.class, username, password).getAllAppUsers();
                call.enqueue(new Callback<List<AppUser>>() {
                    @Override
                    public void onResponse(Call<List<AppUser>> call, Response<List<AppUser>> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AppUser>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Wrong credentials ", Toast.LENGTH_LONG).show();
                    }

                });
            } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        findViewById(R.id.RegisterButton).setOnClickListener(v -> startActivity(new Intent(
                MainActivity.this, RegisterActivity.class)));
    }
}