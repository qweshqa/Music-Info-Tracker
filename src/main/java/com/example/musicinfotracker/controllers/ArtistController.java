package com.example.musicinfotracker.controllers;

import com.example.musicinfotracker.dto.Album;
import com.example.musicinfotracker.dto.Artist;
import com.example.musicinfotracker.dto.Track;
import com.example.musicinfotracker.services.ArtistService;
import com.example.musicinfotracker.utils.ArtistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/artist")
@Controller
public class ArtistController {
    private ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService){
        this.artistService = artistService;
    }

    @GetMapping("/{id}")
    public String viewArtist(@PathVariable("id") String id, Model model) throws IOException, InterruptedException {
        Artist artist;
        List<Track> artistTopTracks;
        List<Album> artistAlbums;
        List<Artist> relatedArtists;

        try{
            artist = artistService.getArtist(id);
            artistTopTracks = artistService.getArtistTopTracks(id);
            artistAlbums = artistService.getArtistAlbums(id, 5);
            relatedArtists = artistService.getArtistRelatedArtists(id);
        } catch (ArtistNotFoundException ignore){
            model.addAttribute("errorMsg", "Artist wasn't found");
            return "404page";
        }

        model.addAttribute("artist", artist);
        model.addAttribute("artist_top_tracks", artistTopTracks);
        model.addAttribute("artist_albums", artistAlbums);
        model.addAttribute("related_artists", relatedArtists);

        return "artist/artist";
    }

    @GetMapping("/{id}/albums")
    public String viewArtistAlbums(@PathVariable("id") String id, Model model) throws IOException, InterruptedException{
        List<Album> artistAlbums;
        Artist artist;

        try{
            artistAlbums = artistService.getArtistAlbums(id, 50);
            artist = artistService.getArtist(id);
        } catch (ArtistNotFoundException ignored){
            model.addAttribute("errorMsg", "Artist wasn't found");
            return "404page";
        }

        model.addAttribute("artist_albums", artistAlbums);
        model.addAttribute("artist", artist);
        return "artist/albums";
    }

    @GetMapping("/{id}/related_artists")
    public String viewArtistRelatedArtists(@PathVariable("id") String id, Model model) throws IOException, InterruptedException {
        List<Artist> related_artists = new ArrayList<>();

        try{
            related_artists = artistService.getArtistRelatedArtists(id);
        } catch (ArtistNotFoundException ignored){
            model.addAttribute("errorMsg", "Artist wasn't found");
            return "404page";
        }

        model.addAttribute("related_artists", related_artists);
        return "artist/related_artists";
    }
}
