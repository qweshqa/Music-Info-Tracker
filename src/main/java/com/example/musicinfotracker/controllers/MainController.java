package com.example.musicinfotracker.controllers;

import com.example.musicinfotracker.dto.Artist;
import com.example.musicinfotracker.services.SearchService;
import com.example.musicinfotracker.utils.ArtistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        return "mainPage";
    }


    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) throws IOException, InterruptedException {
        List<Artist> foundArtists;

        try{
            foundArtists = searchService.searchArtists(query);
        } catch (ArtistNotFoundException ignored){
            model.addAttribute("errorMsg", "Nothing found for the query " + query);
            return "errorPage";
        }

        model.addAttribute("query", query);
        model.addAttribute("artists", foundArtists);

        return "searchResults";
    }
}
