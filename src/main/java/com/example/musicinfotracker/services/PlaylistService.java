package com.example.musicinfotracker.services;

import com.example.musicinfotracker.dto.Playlist;
import com.example.musicinfotracker.utils.PlaylistNotFoundException;
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
public class PlaylistService {

    private String accessToken;

    @Autowired
    public PlaylistService(@Qualifier("accessToken") String accessToken) { this.accessToken = accessToken; }

    public Playlist getPlaylist(String id) throws IOException, InterruptedException{
        String requestUrl = "https://api.spotify.com/v1/playlists/" + id;

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

            Playlist playlist = new Playlist();

            playlist.setId(jsonNode.get("id").asText());
            playlist.setName(jsonNode.get("name").asText());
            playlist.setOwner(jsonNode.get("owner").get("display_name").asText());
            playlist.setImageSource(jsonNode.get("images").get(0).get("url").asText());
            playlist.setPublicStatus(jsonNode.get("public").asBoolean());
            playlist.setDescription(jsonNode.get("description").asText());
            playlist.setFollowers(jsonNode.get("followers").get("total").asInt());

            return playlist;
        }
        throw new PlaylistNotFoundException();
    }
}
