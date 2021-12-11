package com.example.clientapp.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.DashboardActivity;
import com.example.clientapp.Football.APIClient;
import com.example.clientapp.Football.Model.AppUser;
import com.example.clientapp.R;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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

        findViewById(R.id.RegisterButtonRegister).setOnClickListener(v ->
        {
            if (TextUtils.isEmpty(etUsername.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                Toast.makeText(LoginActivity.this, "Username / Password Required", Toast.LENGTH_LONG).show();
            } else {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        Call<String> call1 = APIClient.createService(AuthService.class, username, password).checkAppUser(username);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.raw().code() == HttpsURLConnection.HTTP_MOVED_TEMP) {
                    Prefs.getInstance(getApplicationContext()).save(username, password);
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Wrong credentials ", Toast.LENGTH_LONG).show();
                Log.d("Login", Log.getStackTraceString(t));
            }
        });


    }
}
