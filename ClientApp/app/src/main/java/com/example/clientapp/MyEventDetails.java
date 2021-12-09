package com.example.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.BasketballEvent.Basketball;
import com.example.clientapp.Football.Model.FootballEvent;
import com.example.clientapp.VolleyballEvent.Volleyball;

import java.io.Serializable;

public class MyEventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_event_details_layout);

        TextView locationDetails = findViewById(R.id.locationDetails);
        TextView iconTextViewDetails = findViewById(R.id.iconTextViewDetails);
        TextView dateTextViewDetails = findViewById(R.id.dateTextViewDetails);
        TextView timeTextViewDetails = findViewById(R.id.timeTextViewDetails);
        TextView lvlTextViewDetails = findViewById(R.id.lvlTextViewDetails);
        TextView noteTextViewDetails = findViewById(R.id.noteTextViewDetails);
        TextView vacanciesTextViewDetails = findViewById(R.id.vacanciesTextViewDetails);
        RecyclerView recyclerViewDetails = findViewById(R.id.recyclerViewDetails);

        Object extras = getIntent().getExtras().get("object");

        if (extras instanceof FootballEvent){
            Toast.makeText(getApplicationContext(), "football", Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(getContext(), MyEventDetails.class).putExtra("object", (Serializable) o));
        } else if (extras instanceof Basketball){
            Toast.makeText(getApplicationContext(), "Basketball", Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(getContext(), MyEventDetails.class).putExtra("object", (Serializable) o));
        } else if (extras instanceof Volleyball){
            Toast.makeText(getApplicationContext(), "Volleyball", Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(getContext(), MyEventDetails.class).putExtra("object", (Serializable) o));
        }

        findViewById(R.id.imageButtonComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.imageButtonParticipants).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.imageButtonResign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
