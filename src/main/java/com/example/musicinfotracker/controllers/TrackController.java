package com.example.musicinfotracker.controllers;

import com.example.musicinfotracker.dto.Track;
import com.example.musicinfotracker.services.TrackService;
import com.example.musicinfotracker.utils.TrackNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/track")
public class TrackController {
    private final TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/{id}")
    public String viewTrack(@PathVariable("id") String id, Model model) throws IOException, InterruptedException {
        Track track;
        try{
            track = trackService.getTrack(id);
        } catch (TrackNotFoundException ignored){
            model.addAttribute("errorMsg", "Track with this id does not exist");
            return "errorPage";
        }
        model.addAttribute("track", track);
        return "view/track";
    }
}
