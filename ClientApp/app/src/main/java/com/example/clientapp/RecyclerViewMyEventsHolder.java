package com.example.clientapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewMyEventsHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public RecyclerViewMyEventsHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tvMEHName);
    }

    public TextView getTextView(){
        return textView;
    }
}
