package com.example.musicinfotracker.dto;

import java.util.List;

public class Track {
    private String id;
    private String name;
    private Album album;
    private int trackNumberInAlbum;
    private int durationMs;
    private List<Artist> artists;
    private String preview_url;
    private String imageSource;

    public Track(){

    }
    public Track(String id, String name, Album album, int track_number_in_album, int durationMs,
                 List<Artist> artists, String preview_url, String imageSource) {
        this.id = id;
        this.name = name;
        this.album = album;
        this.trackNumberInAlbum = track_number_in_album;
        this.durationMs = durationMs;
        this.artists = artists;
        this.preview_url = preview_url;
        this.imageSource = imageSource;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getTrackNumberInAlbum() {
        return trackNumberInAlbum;
    }

    public void setTrackNumberInAlbum(int trackNumberInAlbum) {
        this.trackNumberInAlbum = trackNumberInAlbum;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }
    public String getDurationToDisplay(){
        int minutes = (durationMs / 1000) / 60;
        int seconds = (durationMs / 1000) % 60;

        if(seconds < 10){
            return minutes + ":0" + seconds;
        }
        return minutes + ":" + seconds;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void addArtist(Artist artist) { artists.add(artist); }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }
}
