package com.example.musicinfotracker.dto;

import java.util.List;

public class Track {
    private String title;
    private Album album;
    private List<Artist> artists;
    private String lyrics;
    private int popularity;

    public Track(){

    }
    public Track(String title, Album album, String lyrics, List<Artist> artists, int popularity) {
        this.title = title;
        this.album = album;
        this.lyrics = lyrics;
        this.artists = artists;
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
