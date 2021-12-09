package com.example.clientapp.Football.Model;

import java.io.Serializable;

public class Comment implements Serializable {

    private Long id;

    private String text;

    private FootballEvent footballEvent;

    public Comment() {
    }
}
