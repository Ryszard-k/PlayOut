package com.example.clientapp.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.FootballEvent.APIClient;
import com.example.clientapp.FootballEvent.DashboardActivity;
import com.example.clientapp.FootballEvent.Model.AppUser;
import com.example.clientapp.FootballEvent.Model.LoginRequest;
import com.example.clientapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.clientapp.R.layout.login_activity);

        etUsername = findViewById(com.example.clientapp.R.id.etUsername);
        etPassword = findViewById(com.example.clientapp.R.id.etPassword);

        findViewById(R.id.LoginButtonLogin).setOnClickListener(v ->
        {
            if (TextUtils.isEmpty(etUsername.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                Toast.makeText(LoginActivity.this, "Username / Password Required", Toast.LENGTH_LONG).show();
            } else {
                loginUser();
            }
        });
    }

    private void loginUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(etUsername.getText().toString());
        loginRequest.setPassword(etPassword.getText().toString());

        Call<List<AppUser>> call = APIClient.createService(LoginService.class, loginRequest.getUsername(), loginRequest.getPassword()).getAllAppUsers();
        call.enqueue(new Callback<List<AppUser>>() {
            @Override
            public void onResponse(Call<List<AppUser>> call, Response<List<AppUser>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    List<AppUser> abc = response.body();
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    System.out.println(abc);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AppUser>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
