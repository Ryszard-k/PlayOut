package com.example.clientapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.FootballEvent.Model.EventLevel;
import com.example.clientapp.FootballEvent.Model.FootballEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddFootballEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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

                newFootballEvent.setDate(LocalDate.parse(date.getText().toString()));
                newFootballEvent.setTime(LocalTime.parse(time.getText().toString()));
                newFootballEvent.setVacancies(Integer.parseInt(vacancies.getText().toString()));
                newFootballEvent.setNote(note.getText().toString());
                newFootballEvent.setLatitude(extras.getDouble("latitude"));
                newFootballEvent.setLongitude(extras.getDouble("longitude"));
                newFootballEvent.setLocation(locationName);

            }
        });

        date.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            picker = new DatePickerDialog(AddFootballEventActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> date.setText(LocalDate.of(year1, monthOfYear + 1, dayOfMonth)
                            .toString()), year, month, day);
            picker.show();
        });

        time.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR);
            int minutes = cldr.get(Calendar.MINUTE);
            timePicker = new TimePickerDialog(AddFootballEventActivity.this, (view, hourOfDay, minute) ->
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
