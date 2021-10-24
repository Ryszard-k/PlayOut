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
    private String note;

    public SportEvent(LocalDate date, LocalTime time, double latitude, double longitude) {
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
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
}
