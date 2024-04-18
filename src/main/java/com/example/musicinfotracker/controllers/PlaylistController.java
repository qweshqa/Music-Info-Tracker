package com.example.musicinfotracker.controllers;

import com.example.musicinfotracker.dto.Playlist;
import com.example.musicinfotracker.services.PlaylistService;
import com.example.musicinfotracker.utils.PlaylistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {
    public PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService){
        this.playlistService = playlistService;
    }

    @GetMapping("/{id}")
    public String viewPlaylist(@PathVariable("id") String id, Model model) throws IOException, InterruptedException {
        Playlist playlist;

        try{
            playlist = playlistService.getPlaylist(id);
        } catch (PlaylistNotFoundException ignored){
            model.addAttribute("errorMsg", "Playlist wasn't found");
            return "404page";
        }
        model.addAttribute("playlist", playlist);
        return "playlist";
    }
}
