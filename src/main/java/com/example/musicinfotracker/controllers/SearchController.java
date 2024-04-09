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
    public String search(@RequestParam("query") String query, Model model) throws IOException, InterruptedException {
        List<Artist> foundArtists;

        foundArtists = searchService.searchArtists(query);

        model.addAttribute("query", query);
        if(!foundArtists.isEmpty()){
            model.addAttribute("artists", foundArtists);
        } else model.addAttribute("artists_not_found", "No one artist was found");

        return "search/foundArtists";
    }
}
