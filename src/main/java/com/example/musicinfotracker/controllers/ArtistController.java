package com.example.musicinfotracker.controllers;

import com.example.musicinfotracker.dto.Artist;
import com.example.musicinfotracker.dto.Track;
import com.example.musicinfotracker.services.ArtistService;
import com.example.musicinfotracker.utils.ArtistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private String accessToken;
    private ArtistService artistService;

    @Autowired
    public ArtistController(@Qualifier("accessToken") String accessToken, ArtistService artistService){
        this.artistService = artistService;
        this.accessToken = accessToken;
    }

    @GetMapping("/{id}")
    public String viewArtist(@PathVariable("id") String id, Model model) throws IOException, InterruptedException {
        Artist artist;
        List<Track> artistTopTracks;
        List<Artist> relatedArtists;

        try{
            artist = artistService.getArtist(id);
            artistTopTracks = artistService.getArtistTopTracks(id);
            relatedArtists = artistService.getArtistRelatedArtists(id);
        } catch (ArtistNotFoundException ignore){
            model.addAttribute("errorMsg", "Artist wasn't found");
            return "errorPage";
        }

        model.addAttribute("artist", artist);
        model.addAttribute("artist_top_tracks", artistTopTracks);
        model.addAttribute("related_artists", relatedArtists);

        return "/view/artist";
    }

    @GetMapping("/{id}/related_artists")
    public String viewArtistRelatedArtists(@PathVariable("id") String id, Model model) throws IOException, InterruptedException {
        List<Artist> related_artists = new ArrayList<>();

        try{
            related_artists = artistService.getArtistRelatedArtists(id);
        } catch (ArtistNotFoundException ignored){
            model.addAttribute("errorMsg", "Artist wasn't found");
            return "errorPage";
        }

        model.addAttribute("related_artists", related_artists);
        return "artist/related_artists";
    }
}
