package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.clientapp.FootballEvent.APIClient;
import com.example.clientapp.FootballEvent.Model.FootballEvent;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewFootballEvent);

        getAllEvent();
    }

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
        }
}