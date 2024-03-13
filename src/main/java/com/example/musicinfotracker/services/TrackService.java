package com.example.musicinfotracker.services;

import com.example.musicinfotracker.dto.Album;
import com.example.musicinfotracker.dto.Artist;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
            track.setAlbum(new Album());

            // parse release_date from string to LocalDate to be able to display it in any format
            String releaseDateString = jsonNode.get("album").get("release_date").asText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            track.getAlbum().setReleaseDate(LocalDate.parse(releaseDateString, formatter));

            track.getAlbum().setName(jsonNode.get("album").get("name").asText());
            track.getAlbum().setId(jsonNode.get("album").get("id").asText());
            track.setTrackNumberInAlbum(jsonNode.get("track_number").asInt());
            track.setId(jsonNode.get("id").asText());
            track.setPreview_url(jsonNode.get("preview_url").asText());
            track.setImageSource(jsonNode.get("album").get("images").get(0).get("url").asText());

            track.setArtists(new ArrayList<>());
            for(int i = 0; i < jsonNode.get("artists").size(); i++){
                Artist artist = new Artist();

                artist.setId(jsonNode.get("artists").get(i).get("id").asText());
                artist.setName(jsonNode.get("artists").get(i).get("name").asText());

                track.addArtist(artist);
            }

            return track;
        }
        else{
            throw new TrackNotFoundException();
        }
    }
}
