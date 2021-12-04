package com.example.clientapp.FootballEvent;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.Basketball.Basketball;
import com.example.clientapp.FootballEvent.Model.FootballEvent;
import com.example.clientapp.R;
import com.example.clientapp.RecyclerViewMyEventsHolder;
import com.example.clientapp.Volleyball.Volleyball;

import java.util.List;

public class ActiveFootballEvents extends RecyclerView.Adapter<RecyclerViewMyEventsHolder> {

    private final List<FootballEvent> footballs;
    private final List<Basketball> basketballs;
    private final List<Volleyball> volleyballs;

    public ActiveFootballEvents(List<FootballEvent> list, List<Basketball> basketballs, List<Volleyball> volleyballs) {
        this.footballs = list;
        this.basketballs = basketballs;
        this.volleyballs = volleyballs;
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
        if ((!footballs.isEmpty()) && (position < footballs.size())) {
            holder.getDateTextView().setText(footballs.get(position).getDate().toString());
            holder.getTimeTextView().setText(footballs.get(position).getTime().toString());
            holder.getLvlTextView().setText(footballs.get(position).getEventLevel().toString());
            holder.getNoteTextView().setText(footballs.get(position).getNote());
            holder.getTvMEHLocation().setText(footballs.get(position).getLocation());
            holder.getIconTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_soccer_24, 0, 0, 0);

        } else if ((!basketballs.isEmpty()) && ((position - footballs.size()) < basketballs.size())){
            holder.getDateTextView().setText(basketballs.get(position - footballs.size()).getDate().toString());
            holder.getTimeTextView().setText(basketballs.get(position - footballs.size()).getTime().toString());
            holder.getLvlTextView().setText(basketballs.get(position - footballs.size()).getEventLevel().toString());
            holder.getNoteTextView().setText(basketballs.get(position - footballs.size()).getNote());
            holder.getTvMEHLocation().setText(basketballs.get(position - footballs.size()).getLocation());
            holder.getIconTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_basketball_24, 0, 0, 0);

        } else if ((!volleyballs.isEmpty()) && ((position - footballs.size() - basketballs.size()) < volleyballs.size())){
            holder.getDateTextView().setText(volleyballs.get(position - footballs.size() - basketballs.size()).getDate().toString());
            holder.getTimeTextView().setText(volleyballs.get(position - footballs.size() - basketballs.size()).getTime().toString());
            holder.getLvlTextView().setText(volleyballs.get(position - footballs.size() - basketballs.size()).getEventLevel().toString());
            holder.getNoteTextView().setText(volleyballs.get(position - footballs.size() - basketballs.size()).getNote());
            holder.getTvMEHLocation().setText(volleyballs.get(position - footballs.size() - basketballs.size()).getLocation());
            holder.getIconTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_volleyball_24, 0, 0, 0);

        }
    }

    @Override
    public int getItemCount() {
        return (footballs.size() + basketballs.size() + volleyballs.size());
    }

}
