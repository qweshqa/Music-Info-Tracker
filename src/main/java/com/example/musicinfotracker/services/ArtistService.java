package com.example.musicinfotracker.services;

import com.example.musicinfotracker.dto.Artist;
import com.example.musicinfotracker.utils.ArtistNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Service
public class ArtistService {
    private String accessToken;

    @Autowired
    public ArtistService(@Qualifier("accessToken") String accessToken){
        this.accessToken = accessToken;
    }

    public Artist getArtist(String id) throws IOException, InterruptedException {
        String requestUrl = "https://api.spotify.com/v1/artists/" + id;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200){
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(response.body());

            Artist artist = new Artist();

            artist.setGenres(new ArrayList<>());
            artist.setId(jsonNode.get("id").asText());
            try{
                artist.setImageSource(jsonNode.get("images").get(2).get("url").asText());
            } catch (NullPointerException ignored){
                // if artist hasn't got any images, then continue
            }
            artist.setName(jsonNode.get("name").asText());
            artist.setFollowers(jsonNode.get("followers").get("total").asInt());
            for (int i = 0; i < jsonNode.get("genres").size(); i++) {
                artist.addGenre(jsonNode.get("genres").get(i).asText());
            }

            return artist;
        }
        throw new ArtistNotFoundException();
    }
}
