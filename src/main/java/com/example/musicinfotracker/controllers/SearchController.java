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
@RequestMapping("/musicInfoTracker/search")
public class SearchController {
    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    @GetMapping("/artists")
    public String searchArtists(@RequestParam("query") String query, Model model) throws IOException, InterruptedException {
        List<Artist> foundArtists;

        foundArtists = searchService.searchArtists(query);

        model.addAttribute("query", query);
        if(!foundArtists.isEmpty()){
            model.addAttribute("artists", foundArtists);
        } else model.addAttribute("artists_not_found", "No one artist was found");

        return "search/foundArtists";
    }

    @GetMapping("/tracks")
    public String searchTracks(@RequestParam("query") String query, Model model) throws IOException, InterruptedException {
        List<Track> foundTracks;

        foundTracks = searchService.searchTracks(query);

        model.addAttribute("query", query);

        if(!foundTracks.isEmpty()){
            model.addAttribute("tracks", foundTracks);
        } else model.addAttribute("tracks_not_found", "No one track was not found");

        return "search/foundTracks";
    }
    @GetMapping("/albums")
    public String search(@RequestParam("query") String query, Model model) throws IOException, InterruptedException {
        List<Album> foundAlbums;

        foundAlbums = searchService.searchAlbums(query);

        model.addAttribute("query", query);

        if(!foundAlbums.isEmpty()){
            model.addAttribute("albums", foundAlbums);
        } else model.addAttribute("albums_not_found", "No one album was found");

        return "search/foundAlbums";
    }
}
