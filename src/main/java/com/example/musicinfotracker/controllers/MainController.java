package com.example.musicinfotracker.controllers;

import com.example.musicinfotracker.dto.Album;
import com.example.musicinfotracker.dto.Artist;
import com.example.musicinfotracker.dto.Track;
import com.example.musicinfotracker.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/musicInfoTracker")
public class MainController {
    private SearchService searchService;

    @Autowired
    public MainController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping()
    public String mainPage(){
        return "home";
    }


    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) throws IOException, InterruptedException {
        List<Artist> foundArtists;
        List<Track> foundTracks;
        List<Album> foundAlbums;

        foundArtists = searchService.searchArtists(query, 5);
        foundTracks = searchService.searchTracks(query, 5);
        foundAlbums = searchService.searchAlbums(query, 5);

        model.addAttribute("query", query);
        if(!foundArtists.isEmpty()){
            model.addAttribute("artists", foundArtists);
        } else model.addAttribute("artists_not_found", "No one artist was found");

        if(!foundTracks.isEmpty()){
            model.addAttribute("tracks", foundTracks);
        } else model.addAttribute("tracks_not_found", "No one track was not found");

        if(!foundAlbums.isEmpty()){
            model.addAttribute("albums", foundAlbums);
        } else model.addAttribute("albums_not_found", "No one album was found");

        return "search/searchResults";
    }
}
