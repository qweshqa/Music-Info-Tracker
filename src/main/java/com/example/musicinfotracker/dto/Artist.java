package com.example.musicinfotracker.dto;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    private String id;
    private String imageSource;
    private String name;
    private int followers;
    private List<Album> albums;
    private List<String> genres;

    public Artist() {
    }

    public Artist(String id, String imageSource, String name, int followers, List<Album> albums, List<String> genres) {
        this.id = id;
        this.imageSource = imageSource;
        this.name = name;
        this.followers = followers;
        this.albums = albums;
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<String> getGenres() {
        return genres;
    }
    public void addGenre(String genre){
        genres.add(genre);
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
