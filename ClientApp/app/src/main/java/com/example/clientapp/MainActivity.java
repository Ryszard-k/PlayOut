package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.clientapp.Register.LoginActivity;
import com.example.clientapp.Register.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.RegisterButton).setOnClickListener(v -> startActivity(new Intent(
                MainActivity.this, RegisterActivity.class)));

        findViewById(R.id.LoginGoogleButton).setOnClickListener(v -> startActivity(new Intent(
                MainActivity.this, LoginActivity.class)));
    }
/*
    private void getAllEvent() {
        Call<List<FootballEvent>> call = APIClient.getInstance().getMyApi().getAllEvent();

        call.enqueue(new Callback<List<FootballEvent>>() {
            @Override
            public void onResponse(@NonNull Call<List<FootballEvent>> call, @NonNull Response<List<FootballEvent>> response) {
                List<FootballEvent> footballEventList = response.body();

                for (FootballEvent f : footballEventList){
                    String content = "";
                    content += "id " + f.getId() +"\n";
                    content += "date " + f.getDate() +"\n";
                    content += "latitude " + f.getLatitude() +"\n";
                    content += "longitude " + f.getLongitude() +"\n";
                    listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, Collections.singletonList(content)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FootballEvent>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("TAG","Response = "+t.toString());
            }
        });
        }*/
}