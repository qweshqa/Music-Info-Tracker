package com.example.musicinfotracker.controllers;

import com.example.musicinfotracker.dto.Album;
import com.example.musicinfotracker.services.AlbumService;
import com.example.musicinfotracker.utils.AlbumNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/albums")
public class AlbumController {
    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{id}")
    public String getAlbum(@PathVariable("id") String id, Model model) throws IOException, InterruptedException {
        Album album = new Album();

        try {
            album = albumService.getAlbum(id);
        } catch (AlbumNotFoundException ignored){
            model.addAttribute("errorMsg", "Album wasn't found");
            return "404page";
        }
        model.addAttribute("album", album);
        return "album/album";
    }
}
