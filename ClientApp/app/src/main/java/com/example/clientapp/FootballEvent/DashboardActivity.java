package com.example.clientapp.FootballEvent;

import static android.os.Build.VERSION_CODES.R;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.FootballEvent.Model.AppUser;
import com.example.clientapp.FootballEvent.Model.FootballEvent;
import com.example.clientapp.R;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.clientapp.R.layout.dashboard_activity);

        listView = findViewById(com.example.clientapp.R.id.listview);

        getAllEvent();
    }

    private void getAllEvent() {
        Call<List<FootballEvent>> call = APIClient.createService(FootballEventAPI.class).getAllEvent();

        call.enqueue(new Callback<List<FootballEvent>>() {
            @Override
            public void onResponse(@NonNull Call<List<FootballEvent>> call, @NonNull Response<List<FootballEvent>> response) {
                List<FootballEvent> footballEventList = response.body();

                for (FootballEvent f : footballEventList) {
                    String content = "";
                    content += "id " + f.getId() + "\n";
                    content += "date " + f.getDate() + "\n";
                    content += "latitude " + f.getLatitude() + "\n";
                    content += "longitude " + f.getLongitude() + "\n";
                    listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, Collections.singletonList(content)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FootballEvent>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
}
