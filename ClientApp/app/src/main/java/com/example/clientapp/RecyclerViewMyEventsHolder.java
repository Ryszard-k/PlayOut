package com.example.clientapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewMyEventsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView tvMEHLocation;
    private final TextView iconTextView;
    private final TextView dateTextView;
    private final TextView timeTextView;
    private final TextView lvlTextView;
    private final TextView noteTextView;
    private final TextView vacanciesTextView;
    private final EventClickListener eventClickListener;

    public RecyclerViewMyEventsHolder(@NonNull View itemView, EventClickListener eventClickListener) {
        super(itemView);
        tvMEHLocation = itemView.findViewById(R.id.tvMEHLocation);
        iconTextView = itemView.findViewById(R.id.iconTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
        timeTextView = itemView.findViewById(R.id.timeTextView);
        lvlTextView = itemView.findViewById(R.id.lvlTextView);
        noteTextView = itemView.findViewById(R.id.noteTextView);
        vacanciesTextView = itemView.findViewById(R.id.vacanciesTextView);

        this.eventClickListener = eventClickListener;
        itemView.setOnClickListener(this);

    }

    public TextView getTvMEHLocation() {
        return tvMEHLocation;
    }

    public TextView getIconTextView() {
        return iconTextView;
    }

    public TextView getDateTextView() {
        return dateTextView;
    }

    public TextView getTimeTextView() {
        return timeTextView;
    }

    public TextView getLvlTextView() {
        return lvlTextView;
    }

    public TextView getNoteTextView() {
        return noteTextView;
    }

    public TextView getVacanciesTextView() {
        return vacanciesTextView;
    }

    @Override
    public void onClick(View v) {
        eventClickListener.onItemClick(getLayoutPosition(), v);
    }
}
