package com.example.musicinfotracker.dto;

import java.util.List;

public class Playlist {

    private String id;

    private String name;

    private String description;

    private String imageSource;

    private String owner;

    private int followers;

    private boolean publicStatus;

    private List<Track> tracks;

    public Playlist() {
    }

    public Playlist(String id, String name, String description, String imageSource, String owner,
                    int followers, boolean publicStatus, List<Track> tracks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageSource = imageSource;
        this.owner = owner;
        this.followers = followers;
        this.publicStatus = publicStatus;
        this.tracks = tracks;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public boolean isPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(boolean publicStatus) {
        this.publicStatus = publicStatus;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
