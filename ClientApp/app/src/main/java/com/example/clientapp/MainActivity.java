package com.example.clientapp;

import static com.example.clientapp.Authentication.Prefs.MyPREFERENCES;
import static com.example.clientapp.Authentication.Prefs.Password;
import static com.example.clientapp.Authentication.Prefs.Username;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.clientapp.Authentication.AuthService;
import com.example.clientapp.Authentication.RegisterActivity;
import com.example.clientapp.Authentication.LoginActivity;
import com.example.clientapp.Firebase.MyFirebaseMessagingService;
import com.example.clientapp.Football.APIClient;
import com.google.android.gms.common.GoogleApiAvailability;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleApiAvailability googleApiAvailability = new GoogleApiAvailability();
        googleApiAvailability.makeGooglePlayServicesAvailable(this);
        createNotificationChannel();
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        findViewById(R.id.LoginGoogleButton).setOnClickListener(v -> {
            if (sharedpreferences.contains(Username) && sharedpreferences.contains(Password)){
                String username = sharedpreferences.getString(Username, Username);
                String password = sharedpreferences.getString(Password, Password);
                Log.d("mainActivity", sharedpreferences.getString(Username, Username));

                Thread thread = new Thread(() -> {

                    MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService(username);

                    Call<String> call = APIClient.createService(AuthService.class, username, password).checkAppUser(username);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.raw().code() == HttpsURLConnection.HTTP_MOVED_TEMP) {
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                myFirebaseMessagingService.getCurrentToken(username);
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
                });
                thread.start();
            } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        findViewById(R.id.RegisterButton).setOnClickListener(v -> startActivity(new Intent(
                MainActivity.this, RegisterActivity.class)));
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_id";
            String description = "remainder channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}