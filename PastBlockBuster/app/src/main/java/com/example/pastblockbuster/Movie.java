package com.example.pastblockbuster;

public class Movie {
    String name;
    String aud_cum;
    String aud;
    String date;
    String director;
    String actor;
    String time;
    String movieId;

    public Movie(String name, String date, String aud_cum, String aud, String movieId) {
        this.name = name;
        this.date = date;
        this.aud_cum = aud_cum;
        this.aud = aud;
        this.movieId = movieId;
    }


    public Movie(String name, String aud_cum, String aud, String date, String director, String actor, String time, String movieId) {
        this.name = name;
        this.aud_cum = aud_cum;
        this.aud = aud;
        this.date = date;
        this.director = director;
        this.actor = actor;
        this.time = time;
        this.movieId = movieId;
    }
}
