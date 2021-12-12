package com.example.clientapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.Authentication.Prefs;
import com.example.clientapp.BasketballEvent.Basketball;
import com.example.clientapp.Football.Model.AppUser;
import com.example.clientapp.Football.Model.Comment;
import com.example.clientapp.Football.Model.FootballEvent;
import com.example.clientapp.VolleyballEvent.Volleyball;

import java.util.ArrayList;
import java.util.List;

public class MyHistoryDetails extends AppCompatActivity implements EventClickListener{

    private RecyclerView recyclerViewDetailsHistory;
    private List<Comment> commentsList;
    private MyEventDetailsAdapter adapter;
    private List<AppUser> participantsList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_history_details_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView locationDetailsHistory = findViewById(R.id.locationDetailsHistory);
        TextView iconTextViewDetailsHistory = findViewById(R.id.iconTextViewDetailsHistory);
        TextView dateTextViewDetailsHistory = findViewById(R.id.dateTextViewDetailsHistory);
        TextView timeTextViewDetailsHistory = findViewById(R.id.timeTextViewDetailsHistory);
        TextView lvlTextViewDetailsHistory = findViewById(R.id.lvlTextViewDetailsHistory);
        TextView noteTextViewDetailsHistory = findViewById(R.id.TextViewTextCommentHistory);
        TextView vacanciesTextViewDetailsHistory = findViewById(R.id.authorTextViewCommentHistory);
        TextView textViewAuthorDetailsHistory = findViewById(R.id.textViewAuthorDetailsHistory);

        recyclerViewDetailsHistory = findViewById(R.id.recyclerViewDetailsHistory);

        recyclerViewDetailsHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDetailsHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Object extras = getIntent().getExtras().get("object");

        if (extras instanceof FootballEvent){
            locationDetailsHistory.setText("Address: " + ((FootballEvent) extras).getLocation());
            iconTextViewDetailsHistory.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_soccer_24, 0, 0, 0);
            dateTextViewDetailsHistory.setText("Date: " + ((FootballEvent) extras).getDate().toString());
            timeTextViewDetailsHistory.setText("Time: " + ((FootballEvent) extras).getTime().toString());
            lvlTextViewDetailsHistory.setText("Level: " + ((FootballEvent) extras).getEventLevel().toString());
            noteTextViewDetailsHistory.setText("Note: " + ((FootballEvent) extras).getNote());
            vacanciesTextViewDetailsHistory.setText("Vacancies: " + ((FootballEvent) extras).getVacancies());
            textViewAuthorDetailsHistory.setText("Author: " + ((FootballEvent) extras).getAuthor().getUsername());

            participantsList = new ArrayList<>(((FootballEvent) extras).getParticipants());
            commentsList = new ArrayList<>(((FootballEvent) extras).getComments());
            adapter = new MyEventDetailsAdapter(commentsList, this);
            recyclerViewDetailsHistory.setAdapter(adapter);

        } else if (extras instanceof Basketball){
            locationDetailsHistory.setText("Address: " + ((Basketball) extras).getLocation());
            iconTextViewDetailsHistory.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_basketball_24, 0, 0, 0);
            dateTextViewDetailsHistory.setText("Date: " + ((Basketball) extras).getDate().toString());
            timeTextViewDetailsHistory.setText("Time: " + ((Basketball) extras).getTime().toString());
            lvlTextViewDetailsHistory.setText("Level: " + ((Basketball) extras).getEventLevel().toString());
            noteTextViewDetailsHistory.setText("Note: " + ((Basketball) extras).getNote());
            vacanciesTextViewDetailsHistory.setText("Vacancies: " + ((Basketball) extras).getVacancies());
            textViewAuthorDetailsHistory.setText("Author: " + ((Basketball) extras).getAuthorBasketball().getUsername());

            participantsList = new ArrayList<>(((Basketball) extras).getParticipantsBasketball());
            commentsList = new ArrayList<>(((Basketball) extras).getComments());
            adapter = new MyEventDetailsAdapter(commentsList, this);
            recyclerViewDetailsHistory.setAdapter(adapter);

        } else if (extras instanceof Volleyball){
            locationDetailsHistory.setText("Address: " + ((Volleyball) extras).getLocation());
            iconTextViewDetailsHistory.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_volleyball_24, 0, 0, 0);
            dateTextViewDetailsHistory.setText("Date: " + ((Volleyball) extras).getDate().toString());
            timeTextViewDetailsHistory.setText("Time: " + ((Volleyball) extras).getTime().toString());
            lvlTextViewDetailsHistory.setText("Level: " + ((Volleyball) extras).getEventLevel().toString());
            noteTextViewDetailsHistory.setText("Note: " + ((Volleyball) extras).getNote());
            vacanciesTextViewDetailsHistory.setText("Vacancies: " + ((Volleyball) extras).getVacancies());
            textViewAuthorDetailsHistory.setText("Author: " + ((Volleyball) extras).getAuthorVolleyball().getUsername());

            participantsList = new ArrayList<>(((Volleyball) extras).getParticipantsVolleyball());
            commentsList = new ArrayList<>(((Volleyball) extras).getComments());
            adapter = new MyEventDetailsAdapter(commentsList, this);
            recyclerViewDetailsHistory.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(int position, View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        String[] listOfParticipants = new String[participantsList.size()];
        for (int a = 0; a <= participantsList.size() - 1    ; a++){
            listOfParticipants[a] = participantsList.get(a).getUsername();
        }

        builder1.setTitle("List of participants");
        builder1.setItems(listOfParticipants, (dialog, which) -> {

        });

        builder1.setNegativeButton(R.string.close, (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            Prefs.getInstance(getApplicationContext()).clear();
            stopService(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        stopService(new Intent(getApplicationContext(), MyEventDetails.class));
        finish();
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));

        return super.onSupportNavigateUp();
    }
}
