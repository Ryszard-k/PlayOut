package com.example.clientapp.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.FootballEvent.APIClient;
import com.example.clientapp.FootballEvent.DashboardActivity;
import com.example.clientapp.FootballEvent.Model.AppUser;
import com.example.clientapp.MainActivity;
import com.example.clientapp.R;

import java.io.IOException;
import java.net.ConnectException;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        username = findViewById(com.example.clientapp.R.id.etUsername);
        password = findViewById(R.id.etPassword);
        email = findViewById(R.id.editTextEmail);

        findViewById(R.id.RegisterButtonRegister).setOnClickListener(s ->{
            if (validateUserData()) {
                Call<String> call1 = APIClient.createService(AuthService.class).checkAppUser(username.getText().toString());
                call1.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call1, Response<String> response1) {
                        Log.d("tag", String.valueOf(response1));
                        Log.d("tag1", String.valueOf(response1.body()));
                        if (response1.raw().code() == HttpsURLConnection.HTTP_NO_CONTENT) {

                            AppUser newAppUser = new AppUser();
                            newAppUser.setUsername(username.getText().toString());
                            newAppUser.setPassword(password.getText().toString());
                            newAppUser.setEmail(email.getText().toString());

                            Call<String> call = APIClient.createService(AuthService.class).register(newAppUser);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if (response.raw().code() == HttpsURLConnection.HTTP_CREATED){
                                        Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                }
                            });

                        } else {
                            username.setError("This username already exist");
                            username.requestFocus();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call1, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Failed connection username", Toast.LENGTH_LONG).show();
                        Log.d("tag3", String.valueOf(t.getMessage()));
                        Log.d("tag2", String.valueOf(t.getStackTrace()));
                    }
                });
            }
/*
            if (validateUserData() && validateUsernameInDB()){
                AppUser newAppUser = new AppUser();
                newAppUser.setUsername(username.getText().toString());
                newAppUser.setPassword(password.getText().toString());
                newAppUser.setEmail(email.getText().toString());

                Call<String> call = APIClient.createService(AuthService.class).register(newAppUser);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() && response.body().equals("Registered Successfully")){
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Failed connection", Toast.LENGTH_LONG).show();
                        try {
                            throw new ConnectException(t.getMessage());
                        } catch (ConnectException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
*/
        });
    }


    private boolean validateUserData() {

        final String reg_name = username.getText().toString();
        final String reg_email = email.getText().toString();
        final String reg_password = password.getText().toString();

        if (TextUtils.isEmpty(reg_name)) {
            username.setError("Please enter username");
            username.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(reg_email)) {
            email.setError("Please enter email");
            email.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(reg_password)) {
            password.setError("Please enter password");
            password.requestFocus();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(reg_email).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validateUsernameInDB(){
        final String reg_name = username.getText().toString();
        final boolean[] ok = {false};

            Call<String> call1 = APIClient.createService(AuthService.class).checkAppUser(reg_name);
        call1.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call1, Response<String> response1) {
                    Log.d("tag", String.valueOf(response1));
                    Log.d("tag1", String.valueOf(response1.body()));
                    if (response1.raw().code() == HttpsURLConnection.HTTP_NO_CONTENT) {
                            ok[0] = true;
                        Toast.makeText(RegisterActivity.this, "git", Toast.LENGTH_LONG).show();

                    } else {
                        username.setError("This username already exist");
                        username.requestFocus();
                    }

                }

                @Override
                public void onFailure(Call<String> call1, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Failed connection", Toast.LENGTH_LONG).show();
                    Log.d("tag3", String.valueOf(t.getMessage()));
                    Log.d("tag2", String.valueOf(t.getStackTrace()));
                }
            });
            return ok[0];
        }
}
