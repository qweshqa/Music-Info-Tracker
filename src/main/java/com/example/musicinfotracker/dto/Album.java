package com.example.musicinfotracker.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Album {
    private String id;
    private String name;
    private String album_type;
    private String imageSource;
    private LocalDate releaseDate;
    private int totalTracks;
    private List<Track> tracks;
    private List<Artist> artists;
    private String labelInfo;

    public Album(){

    }
    public Album(String id, String name, String album_type, String imageSource, LocalDate releaseDate,
                 int totalTracks, List<Track> tracks, List<Artist> artists, String labelInfo) {
        this.id = id;
        this.name = name;
        this.album_type = album_type;
        this.imageSource = imageSource;
        this.releaseDate = releaseDate;
        this.totalTracks = totalTracks;
        this.tracks = tracks;
        this.artists = artists;
        this.labelInfo = labelInfo;
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

    public String getAlbum_type() { return album_type; }

    public void setAlbum_type(String album_type) { this.album_type = album_type; }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
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

    public int getTotalTracks() { return totalTracks; }

    public void setTotalTracks(int totalTracks) { this.totalTracks = totalTracks; }

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

    public String getLabelInfo() {
        return labelInfo;
    }

    public void setLabelInfo(String labelInfo) {
        this.labelInfo = labelInfo;
    }
}
