package com.example.clientapp;

import static com.example.clientapp.auth.Prefs.MyPREFERENCES;
import static com.example.clientapp.auth.Prefs.Username;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.footballEvent.APIClient;
import com.example.clientapp.footballEvent.FootballEventAPI;
import com.example.clientapp.footballEvent.model.AppUser;
import com.example.clientapp.footballEvent.model.EventLevel;
import com.example.clientapp.footballEvent.model.FootballEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBasketballEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private final String[] lvlList = EventLevel.enumToStringArray();
    private final FootballEvent newFootballEvent = new FootballEvent();
    private final List<String> selectedItems = Arrays.asList(lvlList);
    private EditText date;
    private EditText time;
    private EditText vacancies;
    private EditText note;
    private DatePickerDialog picker;
    private TimePickerDialog timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_football_event_layout);
        Bundle extras = getIntent().getExtras();
        String locationName = Geocoder.nameFromLatLon(getApplicationContext(), extras.getDouble("latitude"), extras.getDouble("longitude"));
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Spinner spinnerLvl = findViewById(R.id.spinnerLvl);
        spinnerLvl.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, lvlList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLvl.setAdapter(aa);

        date = findViewById(R.id.editTextDate);
        time = findViewById(R.id.editTextTime);
        vacancies = findViewById(R.id.editTextNumber);
        vacancies.setInputType(InputType.TYPE_CLASS_NUMBER);
        note = findViewById(R.id.editTextTextMultiLine);

        findViewById(R.id.buttonOk).setOnClickListener(v -> {
            if (validateUserData()){
                AppUser appUser = new AppUser();
                appUser.setUsername(sharedpreferences.getString(Username, Username));

                newFootballEvent.setDate(LocalDate.parse(date.getText().toString()));
                newFootballEvent.setTime(LocalTime.parse(time.getText().toString()));
                newFootballEvent.setVacancies(Integer.parseInt(vacancies.getText().toString()));
                newFootballEvent.setNote(note.getText().toString());
                newFootballEvent.setLatitude(extras.getDouble("latitude"));
                newFootballEvent.setLongitude(extras.getDouble("longitude"));
                newFootballEvent.setLocation(locationName);
                newFootballEvent.setAuthor(appUser);

                Call<Void> call = APIClient.createService(FootballEventAPI.class).addEvent(newFootballEvent);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddBasketballEvent.this, "Created event", Toast.LENGTH_LONG).show();

                            stopService(new Intent(AddBasketballEvent.this, DashboardActivity.class));
                            startActivity(new Intent(AddBasketballEvent.this, DashboardActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddBasketballEvent.this, "nie poszui", Toast.LENGTH_LONG).show();
                        Log.d("dodajemyFE", t.getMessage());
                        Log.d("dodajemyFE2", Log.getStackTraceString(t));
                    }
            });
            }
        });

        findViewById(R.id.buttonCancel).setOnClickListener(v -> finish());

        date.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            picker = new DatePickerDialog(AddBasketballEvent.this,
                    (view, year1, monthOfYear, dayOfMonth) -> date.setText(LocalDate.of(year1, monthOfYear + 1, dayOfMonth)
                            .toString()), year, month, day);
            picker.show();
        });

        time.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR);
            int minutes = cldr.get(Calendar.MINUTE);
            timePicker = new TimePickerDialog(AddBasketballEvent.this, (view, hourOfDay, minute) ->
                time.setText(LocalTime.of(hourOfDay, minute, 0).toString()), hour, minutes, true);
            timePicker.show();
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        List<EventLevel> eventLevelList = EventLevel.returnListOfEnums();
        for (EventLevel e : eventLevelList){
            if (e.name().equals(selectedItems.get(position).substring(0, 1))) {
                newFootballEvent.setEventLevel(e);
                break;
            }
        }
        Log.d("addedEventLevel", newFootballEvent.getEventLevel().name());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        newFootballEvent.setEventLevel(EventLevel.A);
    }

    private boolean validateUserData() {

        final String reg_date = date.getText().toString();
        final String reg_time = time.getText().toString();
        final String reg_vacancies = vacancies.getText().toString();

        if (TextUtils.isEmpty(reg_date)) {
            date.setError("Please enter date");
            date.requestFocus();
            return false;
        } else if (LocalDate.parse(reg_date).isBefore(LocalDate.now())){
            date.setError("Please enter correctly date");
            date.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(reg_time)) {
            time.setError("Please enter time");
            time.requestFocus();
            return false;
        } else if ((LocalDate.parse(reg_date).isEqual(LocalDate.now())) && (LocalTime.parse(reg_time).isBefore(LocalTime.now().plusMinutes(30)))){
            time.setError("Please enter correctly time");
            time.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(reg_vacancies)) {
            vacancies.setError("Please enter vacancies");
            vacancies.requestFocus();
            return false;
        }

        return true;
    }

}
