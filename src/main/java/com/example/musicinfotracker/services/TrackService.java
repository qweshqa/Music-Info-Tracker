package com.example.musicinfotracker.services;

import com.example.musicinfotracker.dto.Track;
import com.example.musicinfotracker.utils.TrackNotFoundException;
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

@Service
public class TrackService {
    private String accessToken;

    @Autowired
    public TrackService(@Qualifier("accessToken") String accessToken){
        this.accessToken = accessToken;
    }
    public Track getTrack(String id) throws IOException, InterruptedException {
        String requestUrl = "https://api.spotify.com/v1/tracks/" + id;

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

            Track track = new Track();

            track.setName(jsonNode.get("name").asText());
            track.setId(jsonNode.get("id").asText());
            track.setPopularity(jsonNode.get("popularity").asInt());
            track.setPreview_url(jsonNode.get("preview_url").asText());
            track.setImageSource(jsonNode.get("album").get("images").get(0).get("url").asText());

            return track;
        }
        else{
            throw new TrackNotFoundException();
        }
    }
}
