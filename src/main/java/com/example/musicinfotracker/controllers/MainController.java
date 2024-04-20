package com.example.musicinfotracker.controllers;

import com.example.musicinfotracker.dto.Album;
import com.example.musicinfotracker.dto.Artist;
import com.example.musicinfotracker.dto.Playlist;
import com.example.musicinfotracker.dto.Track;
import com.example.musicinfotracker.services.AlbumService;
import com.example.musicinfotracker.services.SearchService;
import com.example.musicinfotracker.utils.AlbumNotFoundException;
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

    private AlbumService albumService;

    @Autowired
    public MainController(SearchService searchService, AlbumService albumService) {
        this.searchService = searchService;
        this.albumService = albumService;
    }

    @GetMapping()
    public String mainPage(Model model) throws IOException, InterruptedException {
        // get new releases
        List<Album> new_releases;

        try{
            new_releases = albumService.getNewReleases(25);
        } catch (AlbumNotFoundException ignored){
            model.addAttribute("albums_not_found", "No one release was found");
            return "404page";
        }
        model.addAttribute("new_releases", new_releases);

        return "home";
    }


    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) throws IOException, InterruptedException {
        List<Artist> foundArtists;
        List<Track> foundTracks;
        List<Album> foundAlbums;
        List<Playlist> foundPlaylists;

        foundArtists = searchService.searchArtists(query, 5);
        foundTracks = searchService.searchTracks(query, 5);
        foundAlbums = searchService.searchAlbums(query, 5);
        foundPlaylists = searchService.searchPlaylists(query, 5);

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

        if(!foundPlaylists.isEmpty()){
            model.addAttribute("playlists", foundPlaylists);
        } else model.addAttribute("playlists_not_found", "No one playlist was found");

        return "search/searchResults";
    }
}
