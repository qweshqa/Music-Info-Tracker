package com.example.musicinfotracker.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Album {
    private String id;
    private String name;
    private LocalDate releaseDate;
    private List<Track> tracks;
    private List<Artist> artists;

    public Album(){

    }
    public Album(String id, String name, LocalDate releaseDate, List<Track> tracks, List<Artist> artists) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.tracks = tracks;
        this.artists = artists;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
