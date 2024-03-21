package com.example.musicinfotracker.services;

import com.example.musicinfotracker.dto.Album;
import com.example.musicinfotracker.dto.Artist;
import com.example.musicinfotracker.dto.Track;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
            foundAlbum.setAlbum_type(jsonNode.get("album_type").asText());
            foundAlbum.setTotalTracks(jsonNode.get("total_tracks").asInt());

            String releaseDateString = jsonNode.get("release_date").asText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            foundAlbum.setReleaseDate(LocalDate.parse(releaseDateString, formatter));

            List<Artist> albumArtists = new ArrayList<>();
            for (int i = 0; i < jsonNode.get("artists").size(); i++) {
                Artist artist = new Artist();

                artist.setName(jsonNode.get("artists").get(i).get("name").asText());
                artist.setId(jsonNode.get("artists").get(i).get("id").asText());

                albumArtists.add(artist);
            }
            foundAlbum.setArtists(albumArtists);

            List<Track> albumTracks = new ArrayList<>();
            JsonNode trackNode = jsonNode.get("tracks").get("items");
            for(int i = 0; i < trackNode.size(); i++){
                Track track = new Track();

                track.setId(trackNode.get(i).get("id").asText());
                track.setName(trackNode.get(i).get("name").asText());
                track.setTrackNumberInAlbum(trackNode.get(i).get("track_number").asInt());
                track.setImageSource(foundAlbum.getImageSource());
                track.setPreview_url(trackNode.get(i).get("preview_url").asText());
                track.setDurationMs(trackNode.get(i).get("duration_ms").asInt());
                
                List<Artist> trackArtists = new ArrayList<>();
                JsonNode artistNode = trackNode.get(i).get("artists");
                for (int j = 0; j < artistNode.size(); j++) {
                    Artist artist = new Artist();

                    artist.setId(artistNode.get(j).get("id").asText());
                    artist.setName(artistNode.get(j).get("name").asText());

                    trackArtists.add(artist);
                }
                track.setArtists(trackArtists);

                albumTracks.add(track);
            }
            foundAlbum.setTracks(albumTracks);

            return foundAlbum;
        }
        throw new AlbumNotFoundException();
    }
}
