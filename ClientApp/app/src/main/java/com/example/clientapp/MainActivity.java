package com.example.clientapp;

import static com.example.clientapp.Authentication.Prefs.MyPREFERENCES;
import static com.example.clientapp.Authentication.Prefs.Password;
import static com.example.clientapp.Authentication.Prefs.Username;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.clientapp.Authentication.AuthService;
import com.example.clientapp.Authentication.RegisterActivity;
import com.example.clientapp.Authentication.LoginActivity;
import com.example.clientapp.Football.APIClient;

import javax.net.ssl.HttpsURLConnection;

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
                Log.d("mainActivity", sharedpreferences.getString(Username, Username));

                Call<String> call = APIClient.createService(AuthService.class, username, password).checkAppUser(username);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.raw().code() == HttpsURLConnection.HTTP_MOVED_TEMP) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
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