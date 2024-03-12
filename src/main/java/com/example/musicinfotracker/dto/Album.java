package com.example.musicinfotracker.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Album {
    private int id;
    private String title;
    private LocalDate releaseDate;
    private List<Track> tracks;
    private List<Artist> artists;

    public Album(){

    }
    public Album(int id, String title, LocalDate releaseDate, List<Track> tracks, List<Artist> artists) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.tracks = tracks;
        this.artists = artists;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDateToDisplay(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM. dd, yyyy", Locale.ENGLISH);

        return releaseDate.format(formatter);
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
