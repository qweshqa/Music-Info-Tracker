package com.example.musicinfotracker.dto;

import java.util.List;

public class Track {
    private String id;
    private String name;
    private Album album;
    private List<String> artistsId;
    private String lyrics;
    private int popularity;
    private String preview_url;
    private String imageSource;

    public Track(){

    }
    public Track(String id, String name, Album album, String lyrics, List<String> artistsId,
                 int popularity, String preview_url, String imageSource) {
        this.id = id;
        this.name = name;
        this.album = album;
        this.lyrics = lyrics;
        this.artistsId = artistsId;
        this.popularity = popularity;
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

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public List<String> getArtistsId() {
        return artistsId;
    }

    public void addArtistId(String id) { artistsId.add(id); }

    public void setArtistsId(List<String> artistsId) {
        this.artistsId = artistsId;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
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
