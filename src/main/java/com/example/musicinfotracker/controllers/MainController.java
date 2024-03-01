package com.example.musicinfotracker.controllers;

import com.example.musicinfotracker.dto.Album;
import com.example.musicinfotracker.dto.Artist;
import com.example.musicinfotracker.dto.Track;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Controller
@RequestMapping("/musicInfoTracker")
public class MainController {
    private String accessToken;

    @Autowired
    public MainController(@Qualifier("accessToken") String accessToken) {
        this.accessToken = accessToken;
    }

    @GetMapping()
    public String mainPage(){
        return "mainPage";
    }


    @GetMapping("/search")
    public String search(@RequestParam("query") String query,
                         @RequestParam("searchType") String searchType, Model model) throws IOException, InterruptedException {

        String requestUrl = "https://api.spotify.com/v1/search";
        String requestBody = "q=" + query +
                "&type=" + searchType +
                "&limit=" + 1;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl + "?" + requestBody))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        if(response.statusCode() == 200){
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(response.body());

            switch(searchType){
                case "artist":
                    Artist artist = new Artist();

                    artist.setGenres(new ArrayList<>());

                    artist.setId    (jsonNode.get("artists").get("items").get(0).get("id").asText());
                    artist.setImageSource   (jsonNode.get("artists").get("items").get(0).get("images").get(0).get("url").asText());
                    artist.setName  (jsonNode.get("artists").get("items").get(0).get("name").asText());
                    artist.setFollowers (jsonNode.get("artists").get("items").get(0).get("followers").get("total").asInt());



                    model.addAttribute("artist", artist);
                    return "view/artist";
            }
        }
        return "";
    }
}
