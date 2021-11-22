package com.inz.PlayOut.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@MappedSuperclass
public class SportEvent {

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @Enumerated(EnumType.STRING)
    private EventLevel eventLevel;

    private int vacancies;
    private String note;
    private String location;

    public SportEvent(LocalDate date, LocalTime time, double latitude, double longitude, EventLevel eventLevel, int vacancies, String note, String location) {
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.eventLevel = eventLevel;
        this.vacancies = vacancies;
        this.note = note;
        this.location = location;
    }

    public SportEvent() {
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getNote() {
        return note;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public EventLevel getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(EventLevel eventLevel) {
        this.eventLevel = eventLevel;
    }

    public int getVacancies() {
        return vacancies;
    }

    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
