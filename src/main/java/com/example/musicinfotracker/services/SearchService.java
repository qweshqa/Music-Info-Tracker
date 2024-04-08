package com.example.musicinfotracker.services;

import com.example.musicinfotracker.dto.Album;
import com.example.musicinfotracker.dto.Artist;
import com.example.musicinfotracker.dto.Track;
import com.example.musicinfotracker.utils.AlbumNotFoundException;
import com.example.musicinfotracker.utils.ArtistNotFoundException;
import com.example.musicinfotracker.utils.TrackNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private String accessToken;

    @Autowired
    public SearchService(@Qualifier("accessToken") String accessToken) {
        this.accessToken = accessToken;
    }

    public List<Artist> searchArtists(String query) throws IOException, InterruptedException {
        String requestUrl = "https://api.spotify.com/v1/search";
        // split query on parts and connect by '+' to avoid URISyntaxException
        String[] queryParts = query.split("\\s+");
        String finalQuery = String.join("+", queryParts);
        String requestBody = "q=" + URLEncoder.encode(finalQuery, StandardCharsets.UTF_8) +
                "&type=artist";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl + "?" + requestBody))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200){
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(response.body());
            JsonNode artistNode = jsonNode.get("artists").get("items");

            List<Artist> artists = new ArrayList<>();

            for(int i = 0; i < artistNode.size(); i++){
                Artist artist = new Artist();

                artist.setId(artistNode.get(i).get("id").asText());
                try{
                    artist.setImageSource(artistNode.get(i).get("images").get(0).get("url").asText());
                } catch (NullPointerException ignored){

                }
                artist.setName(artistNode.get(i).get("name").asText());
                
                artists.add(artist);
            }
            return artists;
        }
        return null;
    }

    public List<Track> searchTracks(String query) throws IOException, InterruptedException {
        String requestUrl = "https://api.spotify.com/v1/search";
        String[] queryParts = query.split("\\s+");
        String finalQuery = String.join("+", queryParts);
        String requestBody = "q=" + URLEncoder.encode(finalQuery, StandardCharsets.UTF_8) +
                "&type=track";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl + '?' + requestBody))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200){
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(response.body());
            JsonNode trackNode = jsonNode.get("tracks").get("items");

            List<Track> tracks = new ArrayList<>();

            for (int i = 0; i < trackNode.size(); i++){
                Track track = new Track();

                track.setId(trackNode.get(i).get("id").asText());
                track.setName(trackNode.get(i).get("name").asText());
                track.setImageSource(trackNode.get(i).get("album").get("images").get(0).get("url").asText());

                tracks.add(track);
            }
            return tracks;
        }
        return null;
    }

    public List<Album> searchAlbums(String query) throws IOException, InterruptedException{
        String requestUrl = "https://api.spotify.com/v1/search";
        String[] queryParts = query.split("\\s+");
        String finalQuery = String.join("+", queryParts);
        String requestBody = "q=" + URLEncoder.encode(finalQuery, StandardCharsets.UTF_8) +
                "&type=album";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl + '?' + requestBody))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200){
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(response.body());
            JsonNode albumNode = jsonNode.get("albums").get("items");

            List<Album> foundAlbums = new ArrayList<>();
            for (int i = 0; i < albumNode.size(); i++) {
                Album album = new Album();

                album.setId(albumNode.get(i).get("id").asText());
                album.setName(albumNode.get(i).get("name").asText());
                album.setImageSource(albumNode.get(i).get("images").get(0).get("url").asText());
                if(albumNode.get(i).get("album_type").asText().equals("single") && albumNode.get(i).get("total_tracks").asInt() > 1){
                    album.setAlbum_type("EP");
                } else{
                    album.setAlbum_type(albumNode.get(i).get("album_type").asText());
                }

                foundAlbums.add(album);
            }
            return foundAlbums;
        }
        return null;
    }
}
