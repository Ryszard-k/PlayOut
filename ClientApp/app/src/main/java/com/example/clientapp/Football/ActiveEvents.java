package com.example.clientapp.Football;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.basketball.Basketball;
import com.example.clientapp.EventClickListener;
import com.example.clientapp.Football.Model.FootballEvent;
import com.example.clientapp.R;
import com.example.clientapp.RecyclerViewMyEventsHolder;
import com.example.clientapp.volleyball.Volleyball;

import java.util.List;

public class ActiveEvents extends RecyclerView.Adapter<RecyclerViewMyEventsHolder>{

    private final List<FootballEvent> footballs;
    private final List<Basketball> basketballs;
    private final List<Volleyball> volleyballs;
    private final EventClickListener mEventClickListener;

    public ActiveEvents(List<FootballEvent> list, List<Basketball> basketballs, List<Volleyball> volleyballs, EventClickListener eventClickListener) {
        this.footballs = list;
        this.basketballs = basketballs;
        this.volleyballs = volleyballs;
        this.mEventClickListener = eventClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.rv_my_events_holder;
    }

    @NonNull
    @Override
    public RecyclerViewMyEventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        return new RecyclerViewMyEventsHolder(view, mEventClickListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMyEventsHolder holder, @SuppressLint("RecyclerView") int position) {
        if ((!footballs.isEmpty()) && (position < footballs.size())) {
            holder.getDateTextView().setText("Date: " + footballs.get(position).getDate().toString());
            holder.getTimeTextView().setText("Time: " + footballs.get(position).getTime().toString());
            holder.getLvlTextView().setText("Level: " + footballs.get(position).getEventLevel().toString());
            if (footballs.get(position).getNote() != null) {
                holder.getNoteTextView().setText("Note: " + footballs.get(position).getNote());
            }
            holder.getTvMEHLocation().setText("Address: " + footballs.get(position).getLocation());
            holder.getVacanciesTextView().setText("Vacancies: " + Integer.toString(footballs.get(position).getVacancies()));
            holder.getIconTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_soccer_24, 0, 0, 0);

        } else if ((!basketballs.isEmpty()) && ((position - footballs.size()) < basketballs.size())){
            holder.getDateTextView().setText("Date: " + basketballs.get(position - footballs.size()).getDate().toString());
            holder.getTimeTextView().setText("Time: " + basketballs.get(position - footballs.size()).getTime().toString());
            holder.getLvlTextView().setText("Level: " + basketballs.get(position - footballs.size()).getEventLevel().toString());
            if (basketballs.get(position- footballs.size()).getNote() != null) {
                holder.getNoteTextView().setText("Note: " + basketballs.get(position - footballs.size()).getNote());
            }
            holder.getTvMEHLocation().setText("Address: " + basketballs.get(position - footballs.size()).getLocation());
            holder.getVacanciesTextView().setText("Vacancies: " + Integer.toString(basketballs.get(position - footballs.size()).getVacancies()));
            holder.getIconTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_basketball_24, 0, 0, 0);

        } else if ((!volleyballs.isEmpty()) && ((position - footballs.size() - basketballs.size()) < volleyballs.size())){
            holder.getDateTextView().setText("Date: " + volleyballs.get(position - footballs.size() - basketballs.size()).getDate().toString());
            holder.getTimeTextView().setText("Time: " + volleyballs.get(position - footballs.size() - basketballs.size()).getTime().toString());
            holder.getLvlTextView().setText("Level: " + volleyballs.get(position - footballs.size() - basketballs.size()).getEventLevel().toString());
            if (volleyballs.get(position - footballs.size() - basketballs.size()).getNote() != null) {
                holder.getNoteTextView().setText("Note: " + volleyballs.get(position - footballs.size() - basketballs.size()).getNote());
            }
            holder.getTvMEHLocation().setText("Address: " + volleyballs.get(position - footballs.size() - basketballs.size()).getLocation());
            holder.getVacanciesTextView().setText("Vacancies: " + Integer.toString(volleyballs.get(position - footballs.size() - basketballs.size()).getVacancies()));
            holder.getIconTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_volleyball_24, 0, 0, 0);

        }

    }

    @Override
    public int getItemCount() {
        return (footballs.size() + basketballs.size() + volleyballs.size());
    }


    public Object getItemByPosition(int position){
        if (position < footballs.size()) {
            return footballs.get(position);
        } else if ((position - footballs.size()) < basketballs.size()){
            return basketballs.get(position - footballs.size());
        } else if ((position - footballs.size() - basketballs.size()) < volleyballs.size()){
            return volleyballs.get(position - footballs.size() - basketballs.size());
        }
        return null;
    }

}
