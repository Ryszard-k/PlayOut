package com.example.clientapp;

import static com.example.clientapp.Authentication.Prefs.MyPREFERENCES;
import static com.example.clientapp.Authentication.Prefs.Username;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.Authentication.Prefs;
import com.example.clientapp.BasketballEvent.Basketball;
import com.example.clientapp.BasketballEvent.BasketballAPI;
import com.example.clientapp.Football.APIClient;
import com.example.clientapp.Football.CommentAPI;
import com.example.clientapp.Football.FootballEventAPI;
import com.example.clientapp.Football.Model.AppUser;
import com.example.clientapp.Football.Model.Comment;
import com.example.clientapp.Football.Model.FootballEvent;
import com.example.clientapp.VolleyballEvent.Volleyball;
import com.example.clientapp.VolleyballEvent.VolleyballAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventDetails extends AppCompatActivity implements EventClickListener{

    private List<Comment> commentsList;
    private MyEventDetailsAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerViewDetails;
    private String username;
    private List<AppUser> participantsList;
    private AppUser author;

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_event_details_layout);
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(Username, Username);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView locationDetails = findViewById(R.id.locationDetails);
        TextView iconTextViewDetails = findViewById(R.id.iconTextViewDetails);
        TextView dateTextViewDetails = findViewById(R.id.dateTextViewDetails);
        TextView timeTextViewDetails = findViewById(R.id.timeTextViewDetails);
        TextView lvlTextViewDetails = findViewById(R.id.lvlTextViewDetails);
        TextView noteTextViewDetails = findViewById(R.id.TextViewTextComment);
        TextView vacanciesTextViewDetails = findViewById(R.id.authorTextViewComment);
        TextView textViewAuthorDetails = findViewById(R.id.textViewAuthorDetails);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        recyclerViewDetails = findViewById(R.id.recyclerViewDetails);
        recyclerViewDetails.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDetails.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

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

            participantsList = new ArrayList<>(((FootballEvent) extras).getParticipants());
            commentsList = new ArrayList<>(((FootballEvent) extras).getComments());
            adapter = new MyEventDetailsAdapter(commentsList, this);
            recyclerViewDetails.setAdapter(adapter);
            author = ((FootballEvent) extras).getAuthor();

        } else if (extras instanceof Basketball){
            locationDetails.setText("Address: " + ((Basketball) extras).getLocation());
            iconTextViewDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_basketball_24, 0, 0, 0);
            dateTextViewDetails.setText("Date: " + ((Basketball) extras).getDate().toString());
            timeTextViewDetails.setText("Time: " + ((Basketball) extras).getTime().toString());
            lvlTextViewDetails.setText("Level: " + ((Basketball) extras).getEventLevel().toString());
            noteTextViewDetails.setText("Note: " + ((Basketball) extras).getNote());
            vacanciesTextViewDetails.setText("Vacancies: " + ((Basketball) extras).getVacancies());
            textViewAuthorDetails.setText("Author: " + ((Basketball) extras).getAuthorBasketball().getUsername());

            participantsList = new ArrayList<>(((Basketball) extras).getParticipantsBasketball());
            commentsList = new ArrayList<>(((Basketball) extras).getComments());
            adapter = new MyEventDetailsAdapter(commentsList, this);
            recyclerViewDetails.setAdapter(adapter);
            author = ((Basketball) extras).getAuthorBasketball();

        } else if (extras instanceof Volleyball){
            locationDetails.setText("Address: " + ((Volleyball) extras).getLocation());
            iconTextViewDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_sports_volleyball_24, 0, 0, 0);
            dateTextViewDetails.setText("Date: " + ((Volleyball) extras).getDate().toString());
            timeTextViewDetails.setText("Time: " + ((Volleyball) extras).getTime().toString());
            lvlTextViewDetails.setText("Level: " + ((Volleyball) extras).getEventLevel().toString());
            noteTextViewDetails.setText("Note: " + ((Volleyball) extras).getNote());
            vacanciesTextViewDetails.setText("Vacancies: " + ((Volleyball) extras).getVacancies());
            textViewAuthorDetails.setText("Author: " + ((Volleyball) extras).getAuthorVolleyball().getUsername());

            participantsList = new ArrayList<>(((Volleyball) extras).getParticipantsVolleyball());
            commentsList = new ArrayList<>(((Volleyball) extras).getComments());
            adapter = new MyEventDetailsAdapter(commentsList, this);
            recyclerViewDetails.setAdapter(adapter);
            author = ((Volleyball) extras).getAuthorVolleyball();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.commentMenu:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyEventDetails.this);
                        final EditText edittext = new EditText(getApplicationContext());

                        builder.setTitle("Add comment");
                        builder.setView(edittext);

                        builder.setPositiveButton(R.string.add, (dialog, which) -> {
                            String editTextValue = edittext.getText().toString();
                            AppUser appUser = new AppUser();
                            appUser.setUsername(username);
                            if (editTextValue.isEmpty()) {
                                edittext.setError("Please, add comment");
                                edittext.requestFocus();
                            }
                            Comment comment = new Comment(LocalDate.now(), LocalTime.now(), editTextValue, appUser);

                            if (extras instanceof FootballEvent) {
                                FootballEvent footballEvent = new FootballEvent();
                                footballEvent.setId(((FootballEvent) extras).getId());
                                comment.setFootballEvent(footballEvent);
                            } else if (extras instanceof Basketball) {
                                Basketball basketball = new Basketball();
                                basketball.setId(((Basketball) extras).getId());
                                comment.setBasketballEvent(basketball);
                            } else if (extras instanceof Volleyball) {
                                Volleyball volleyball = new Volleyball();
                                volleyball.setId(((Volleyball) extras).getId());
                                comment.setVolleyballEvent(volleyball);
                            }

                            Thread thread = new Thread(() -> {
                                Call<Long> call = APIClient.createService(CommentAPI.class).addComment(comment);
                                call.enqueue(new Callback<Long>() {
                                    @SuppressLint("NotifyDataSetChanged")
                                    @Override
                                    public void onResponse(Call<Long> call, Response<Long> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Added comment", Toast.LENGTH_SHORT).show();
                                            Long id = response.body();
                                            Log.d("id", String.valueOf(id));
                                            comment.setId(id);
                                            commentsList.add(comment);
                                            adapter.notifyItemInserted(commentsList.size());

                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Long> call, Throwable t) {
                                        Log.d("joinBasketball", Log.getStackTraceString(t));
                                        Toast.makeText(getApplicationContext(), "Pleas add text to comment", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            });
                            thread.start();
                        });

                        builder.setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return true;

                    case R.id.resignMenu:
                        if (author.getUsername().equals(username)) {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(MyEventDetails.this);
                            builder2.setTitle("Are you sure you want to delete your event?");

                            builder2.setPositiveButton(R.string.yes, (dialog, which) -> {
                                Thread thread = new Thread(() -> {
                                if (extras instanceof FootballEvent) {
                                    Call<Void> call2 = APIClient.createService(FootballEventAPI.class).deleteEvent(((FootballEvent) extras).getId());
                                    call2.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call2, Response<Void> response2) {
                                            if (response2.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Event deleted", Toast.LENGTH_SHORT).show();
                                                stopService(new Intent(getApplicationContext(), MyEventDetails.class));
                                                finish();
                                                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call2, Throwable t) {
                                            Log.d("removeFootball", Log.getStackTraceString(t));
                                        }
                                    });
                                } else if (extras instanceof Basketball) {
                                    Call<Void> call3 = APIClient.createService(BasketballAPI.class).deleteEvent(((Basketball) extras).getId());
                                    call3.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call3, Response<Void> response3) {
                                            if (response3.isSuccessful())
                                                Toast.makeText(getApplicationContext(), "Event deleted", Toast.LENGTH_SHORT).show();
                                            stopService(new Intent(getApplicationContext(), MyEventDetails.class));
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call3, Throwable t) {
                                            Log.d("removeBasketball", Log.getStackTraceString(t));
                                        }
                                    });
                                } else if (extras instanceof Volleyball) {
                                    Call<Void> call4 = APIClient.createService(VolleyballAPI.class).deleteEvent(((Volleyball) extras).getId());
                                    call4.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call4, Response<Void> response4) {
                                            if (response4.isSuccessful())
                                                Toast.makeText(getApplicationContext(), "Event deleted", Toast.LENGTH_SHORT).show();
                                            stopService(new Intent(getApplicationContext(), MyEventDetails.class));
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call4, Throwable t) {
                                            Log.d("removeVolleyball", Log.getStackTraceString(t));
                                        }
                                    });
                                }
                                });
                                thread.start();
                            });

                            builder2.setNegativeButton(R.string.cancel, (dialog2, id) -> dialog2.cancel());

                            AlertDialog alertDialog2 = builder2.create();
                            alertDialog2.show();
                        } else {
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(MyEventDetails.this);
                            builder3.setTitle("Are you sure you want to resign for event?");

                            builder3.setPositiveButton(R.string.yes, (dialog, which) -> {
                                Thread thread = new Thread(() -> {
                                    if (extras instanceof FootballEvent) {
                                        Call<Void> call5 = APIClient.createService(FootballEventAPI.class).resignForEvent(((FootballEvent) extras).getId(), username);
                                        call5.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call5, Response<Void> response5) {
                                                if (response5.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Resign for event", Toast.LENGTH_SHORT).show();
                                                    stopService(new Intent(getApplicationContext(), MyEventDetails.class));
                                                    finish();
                                                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call5, Throwable t) {
                                                Log.d("removeVolleyball", Log.getStackTraceString(t));
                                            }
                                        });
                                    } else if (extras instanceof Basketball) {
                                        Call<Void> call6 = APIClient.createService(BasketballAPI.class).resignForEvent(((Basketball) extras).getId(), username);
                                        call6.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call6, Response<Void> response6) {
                                                if (response6.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Resign for event", Toast.LENGTH_SHORT).show();
                                                    stopService(new Intent(getApplicationContext(), MyEventDetails.class));
                                                    finish();
                                                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call6, Throwable t) {
                                                Log.d("removeVolleyball", Log.getStackTraceString(t));
                                            }
                                        });

                                    } else if (extras instanceof Volleyball) {
                                        Call<Void> call7 = APIClient.createService(FootballEventAPI.class).resignForEvent(((Volleyball) extras).getId(), username);
                                        call7.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call7, Response<Void> response7) {
                                                if (response7.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Resign for event", Toast.LENGTH_SHORT).show();
                                                    stopService(new Intent(getApplicationContext(), MyEventDetails.class));
                                                    finish();
                                                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call7, Throwable t) {
                                                Log.d("removeVolleyball", Log.getStackTraceString(t));
                                            }
                                        });
                                    }
                                });
                                thread.start();
                            });

                            builder3.setNegativeButton(R.string.cancel, (dialog2, id) -> dialog2.cancel());

                            AlertDialog alertDialog3 = builder3.create();
                            alertDialog3.show();
                        }
                        return true;

                    case R.id.participantsMenu:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MyEventDetails.this);
                        String[] listOfParticipants = new String[participantsList.size()];
                        for (int a = 0; a <= participantsList.size() - 1; a++) {
                            listOfParticipants[a] = participantsList.get(a).getUsername();
                        }

                        builder1.setTitle("List of participants");
                        builder1.setItems(listOfParticipants, (dialog, which) -> {
                        });

                        builder1.setNegativeButton(R.string.close, (dialog, id) -> dialog.cancel());

                        AlertDialog alertDialog1 = builder1.create();
                        alertDialog1.show();
                        return true;

                }

                return false;

        });
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

    @Override
    public void onItemClick(int position, View view) {
        MyEventDetailsAdapter myEventDetailsAdapter = (MyEventDetailsAdapter) recyclerViewDetails.getAdapter();
        assert myEventDetailsAdapter != null;
        Comment comment = myEventDetailsAdapter.getCommentByPosition(position);

        if (comment.getAuthor().getUsername().equals(username)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MyEventDetails.this);

            builder.setTitle("Are you sure you want to delete comment?");

            builder.setPositiveButton(R.string.ok, (dialog, which) -> {
                Thread thread = new Thread(() -> {
                    Call<Void> call = APIClient.createService(CommentAPI.class).deleteComment(comment.getId());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Deleted comment", Toast.LENGTH_SHORT).show();
                                commentsList.remove(comment);
                                adapter.notifyItemRemoved(position);
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("joinBasketball", Log.getStackTraceString(t));
                        }
                    });
                });
                thread.start();
            });

            builder.setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
