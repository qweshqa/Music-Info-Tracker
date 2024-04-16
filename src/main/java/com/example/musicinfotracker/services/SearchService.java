package com.example.musicinfotracker.services;

import com.example.musicinfotracker.dto.Album;
import com.example.musicinfotracker.dto.Artist;
import com.example.musicinfotracker.dto.Playlist;
import com.example.musicinfotracker.dto.Track;
import com.example.musicinfotracker.utils.AlbumNotFoundException;
import com.example.musicinfotracker.utils.ArtistNotFoundException;
import com.example.musicinfotracker.utils.PlaylistNotFoundException;
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

    public List<Artist> searchArtists(String query, int limit) throws IOException, InterruptedException {
        String requestUrl = "https://api.spotify.com/v1/search";
        // split query on parts and connect by '+' to avoid URISyntaxException
        String[] queryParts = query.split("\\s+");
        String finalQuery = String.join("+", queryParts);
        String requestBody = "q=" + URLEncoder.encode(finalQuery, StandardCharsets.UTF_8) +
                "&type=artist" +
                "&limit=" + limit;

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

    public List<Track> searchTracks(String query, int limit) throws IOException, InterruptedException {
        String requestUrl = "https://api.spotify.com/v1/search";
        String[] queryParts = query.split("\\s+");
        String finalQuery = String.join("+", queryParts);
        String requestBody = "q=" + URLEncoder.encode(finalQuery, StandardCharsets.UTF_8) +
                "&type=track" +
                "&limit=" + limit;;

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

            List<Track> tracks = new ArrayList<>();

            for (int i = 0; i < jsonNode.get("tracks").get("items").size(); i++){
                JsonNode trackNode = jsonNode.get("tracks").get("items").get(i);

                Track track = new Track();

                track.setId(trackNode.get("id").asText());
                track.setName(trackNode.get("name").asText());
                track.setImageSource(trackNode.get("album").get("images").get(0).get("url").asText());
                track.setDurationMs(trackNode.get("duration_ms").asInt());
                track.setPreview_url(trackNode.get("preview_url").asText());

                List<Artist> trackArtists = new ArrayList<>();
                for(int j = 0; j < trackNode.get("artists").size(); j++){
                    JsonNode artistNode = trackNode.get("artists").get(j);
                    Artist artist = new Artist();

                    artist.setId(artistNode.get("id").asText());
                    artist.setName(artistNode.get("name").asText());

                    trackArtists.add(artist);
                }
                track.setArtists(trackArtists);

                tracks.add(track);
            }
            return tracks;
        }
        return null;
    }

    public List<Album> searchAlbums(String query, int limit) throws IOException, InterruptedException{
        String requestUrl = "https://api.spotify.com/v1/search";
        String[] queryParts = query.split("\\s+");
        String finalQuery = String.join("+", queryParts);
        String requestBody = "q=" + URLEncoder.encode(finalQuery, StandardCharsets.UTF_8) +
                "&type=album" +
                "&limit=" + limit;;

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

    public List<Playlist> searchPlaylists(String query, int limit) throws IOException, InterruptedException{
        String requestUrl = "https://api.spotify.com/v1/search";
        String[] queryParts = query.split("\\s+");
        String finalQuery = String.join("+", queryParts);
        String requestBody = "q=" + URLEncoder.encode(finalQuery, StandardCharsets.UTF_8) +
                "&type=playlist" +
                "&limit=" + limit;

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

            List<Playlist> foundPlaylists = new ArrayList<>();
            for(int i = 0; i < jsonNode.get("playlists").get("items").size(); i++){
                JsonNode playlistNode = jsonNode.get("playlists").get("items").get(i);
                Playlist playlist = new Playlist();

                playlist.setId(playlistNode.get("id").asText());
                playlist.setName(playlistNode.get("name").asText());
                playlist.setImageSource(playlistNode.get("images").get(0).get("url").asText());

                foundPlaylists.add(playlist);
            }
            return foundPlaylists;
        }
        throw new PlaylistNotFoundException();
    }
}
