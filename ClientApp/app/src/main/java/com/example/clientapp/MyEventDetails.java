package com.example.clientapp;

import static com.example.clientapp.Authentication.Prefs.MyPREFERENCES;
import static com.example.clientapp.Authentication.Prefs.Username;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.BasketballEvent.Basketball;
import com.example.clientapp.Football.APIClient;
import com.example.clientapp.Football.CommentAPI;
import com.example.clientapp.Football.Model.AppUser;
import com.example.clientapp.Football.Model.Comment;
import com.example.clientapp.Football.Model.FootballEvent;
import com.example.clientapp.VolleyballEvent.Volleyball;

import java.time.LocalDate;
import java.time.LocalTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventDetails extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_event_details_layout);
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        TextView locationDetails = findViewById(R.id.locationDetails);
        TextView iconTextViewDetails = findViewById(R.id.iconTextViewDetails);
        TextView dateTextViewDetails = findViewById(R.id.dateTextViewDetails);
        TextView timeTextViewDetails = findViewById(R.id.timeTextViewDetails);
        TextView lvlTextViewDetails = findViewById(R.id.lvlTextViewDetails);
        TextView noteTextViewDetails = findViewById(R.id.TextViewTextComment);
        TextView vacanciesTextViewDetails = findViewById(R.id.authorTextViewComment);
        TextView textViewAuthorDetails = findViewById(R.id.textViewAuthorDetails);
        RecyclerView recyclerViewDetails = findViewById(R.id.recyclerViewDetails);

        Object extras = getIntent().getExtras().get("object");

        if (extras instanceof FootballEvent){
            locationDetails.setText("Address: " + ((FootballEvent) extras).getLocation());
            iconTextViewDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_soccer_24, 0, 0, 0);
            dateTextViewDetails.setText("Date: " + ((FootballEvent) extras).getDate().toString());
            timeTextViewDetails.setText("Time: " + ((FootballEvent) extras).getTime().toString());
            lvlTextViewDetails.setText("Level: " + ((FootballEvent) extras).getEventLevel().toString());
            noteTextViewDetails.setText("Note: " + ((FootballEvent) extras).getNote());
            vacanciesTextViewDetails.setText("Vacancies: " + ((FootballEvent) extras).getVacancies());
            textViewAuthorDetails.setText("Author: " + ((FootballEvent) extras).getAuthor().getUsername());
            recyclerViewDetails.setAdapter(new MyEventDetailsAdapter(((FootballEvent) extras).getComments()));

        } else if (extras instanceof Basketball){
            locationDetails.setText("Address: " + ((Basketball) extras).getLocation());
            iconTextViewDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_basketball_24, 0, 0, 0);
            dateTextViewDetails.setText("Date: " + ((Basketball) extras).getDate().toString());
            timeTextViewDetails.setText("Time: " + ((Basketball) extras).getTime().toString());
            lvlTextViewDetails.setText("Level: " + ((Basketball) extras).getEventLevel().toString());
            noteTextViewDetails.setText("Note: " + ((Basketball) extras).getNote());
            vacanciesTextViewDetails.setText("Vacancies: " + ((Basketball) extras).getVacancies());
            textViewAuthorDetails.setText("Author: " + ((Basketball) extras).getAuthorBasketball().getUsername());
            recyclerViewDetails.setAdapter(new MyEventDetailsAdapter(((Basketball) extras).getComments()));

        } else if (extras instanceof Volleyball){
            locationDetails.setText("Address: " + ((Volleyball) extras).getLocation());
            iconTextViewDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_volleyball_24, 0, 0, 0);
            dateTextViewDetails.setText("Date: " + ((Volleyball) extras).getDate().toString());
            timeTextViewDetails.setText("Time: " + ((Volleyball) extras).getTime().toString());
            lvlTextViewDetails.setText("Level: " + ((Volleyball) extras).getEventLevel().toString());
            noteTextViewDetails.setText("Note: " + ((Volleyball) extras).getNote());
            vacanciesTextViewDetails.setText("Vacancies: " + ((Volleyball) extras).getVacancies());
            textViewAuthorDetails.setText("Author: " + ((Volleyball) extras).getAuthorVolleyball().getUsername());
            recyclerViewDetails.setAdapter(new MyEventDetailsAdapter(((Volleyball) extras).getComments()));
        }

        findViewById(R.id.imageButtonComment).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MyEventDetails.this);
            final EditText edittext = new EditText(getApplicationContext());

            builder.setTitle("Add comment");
            builder.setView(edittext);

            builder.setPositiveButton(R.string.add, (dialog, which) -> {
                String editTextValue = edittext.getText().toString();
                AppUser appUser = new AppUser();
                appUser.setUsername(sharedpreferences.getString(Username, Username));
                Comment comment = new Comment(LocalDate.now(), LocalTime.now(), editTextValue, appUser);

                if (extras instanceof FootballEvent){
                    FootballEvent footballEvent = new FootballEvent();
                    footballEvent.setId(((FootballEvent) extras).getId());
                    comment.setFootballEvent(footballEvent);
                } else if (extras instanceof Basketball){
                    Basketball basketball = new Basketball();
                    basketball.setId(((Basketball) extras).getId());
                    comment.setBasketball(basketball);
                } else if (extras instanceof Volleyball){
                    Volleyball volleyball = new Volleyball();
                    volleyball.setId(((Volleyball) extras).getId());
                }

                Call<Void> call = APIClient.createService(CommentAPI.class).addComment(comment);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Added comment", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("joinBasketball", Log.getStackTraceString(t));
                        Toast.makeText(getApplicationContext(), "Pleas add text to comment", Toast.LENGTH_SHORT).show();
                    }
                });

            });

            builder.setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
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
