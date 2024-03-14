package com.example.musicinfotracker.services;

import com.example.musicinfotracker.dto.Album;
import com.example.musicinfotracker.utils.AlbumNotFoundException;
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
public class AlbumService {
    private String accessToken;

    @Autowired
    public AlbumService(@Qualifier("accessToken") String accessToken) {
        this.accessToken = accessToken;
    }

    public Album getAlbum(String id) throws IOException, InterruptedException {
        String requestUrl = "https://api.spotify.com/v1/albums/" + id;

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

            Album foundAlbum = new Album();

            foundAlbum.setId(id);
            foundAlbum.setName(jsonNode.get("name").asText());
            foundAlbum.setImageSource(jsonNode.get("images").get(0).get("url").asText());

            return foundAlbum;
        }
        throw new AlbumNotFoundException();
    }
}
