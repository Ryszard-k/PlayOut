package com.example.clientapp.FootballEvent;

import static com.example.clientapp.Auth.Prefs.MyPREFERENCES;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.FootballEvent.Model.FootballEvent;
import com.example.clientapp.R;
import com.example.clientapp.RecyclerViewMyEventsHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFootballEvents extends RecyclerView.Adapter<RecyclerViewMyEventsHolder> {

    private SharedPreferences sharedpreferences;

    @Override
    public int getItemViewType(int position) {
        return R.layout.rv_my_events_holder;
    }

    @NonNull
    @Override
    public RecyclerViewMyEventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        sharedpreferences = parent.getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return new RecyclerViewMyEventsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMyEventsHolder holder, int position) {
        Call<List<FootballEvent>> call = APIClient.createService(FootballEventAPI.class).getMyHistoryEvent("Piotr");
        call.enqueue(new Callback<List<FootballEvent>>() {
            @Override
            public void onResponse(Call<List<FootballEvent>> call, Response<List<FootballEvent>> response) {
                List<FootballEvent> list = response.body();
                holder.getTextView().setText(list.get(0).getNote());
                Log.d("HistoryFootballEvents", "Registered Successfully");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<List<FootballEvent>> call, Throwable t) {
                Log.d("HistoryFootballEventsFailure", t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 100;
    }
}
