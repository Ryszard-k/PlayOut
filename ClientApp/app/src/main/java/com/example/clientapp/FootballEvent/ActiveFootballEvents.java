package com.example.clientapp.FootballEvent;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.FootballEvent.Model.FootballEvent;
import com.example.clientapp.R;
import com.example.clientapp.RecyclerViewMyEventsHolder;

import java.util.List;

public class ActiveFootballEvents extends RecyclerView.Adapter<RecyclerViewMyEventsHolder> {

    private final List<FootballEvent> list;

    public ActiveFootballEvents(List<FootballEvent> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.rv_my_events_holder;
    }

    @NonNull
    @Override
    public RecyclerViewMyEventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewMyEventsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMyEventsHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getTextView().setText(list.get(position).getEventLevel().description);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
