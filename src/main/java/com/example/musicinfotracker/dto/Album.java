package com.example.musicinfotracker.dto;

import java.util.List;

public class Album {
    private String title;
    private List<Track> tracks;
    private List<Artist> artists;

    public Album(){

    }
    public Album(String title, List<Track> tracks, List<Artist> artists) {
        this.title = title;
        this.tracks = tracks;
        this.artists = artists;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
