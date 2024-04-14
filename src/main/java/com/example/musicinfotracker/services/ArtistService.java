package com.example.musicinfotracker.services;

import com.example.musicinfotracker.dto.Album;
import com.example.musicinfotracker.dto.Artist;
import com.example.musicinfotracker.dto.Track;
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
import java.util.List;

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
                artist.setImageSource(jsonNode.get("images").get(0).get("url").asText());
            } catch (NullPointerException ignored){
                
            }
            artist.setName(jsonNode.get("name").asText());
            artist.setFollowers(jsonNode.get("followers").get("total").asInt());
            artist.setSpotifyUrl(jsonNode.get("external_urls").get("spotify").asText());
            for (int i = 0; i < jsonNode.get("genres").size(); i++) {
                artist.addGenre(jsonNode.get("genres").get(i).asText());
            }

            return artist;
        }
        throw new ArtistNotFoundException();
    }

    public List<Track> getArtistTopTracks(String artist_id) throws IOException, InterruptedException{
        String requestUrl = "https://api.spotify.com/v1/artists/" + artist_id + "/top-tracks";

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

            List<Track> artist_top_tracks = new ArrayList<>();

            for(int i = 0; i < jsonNode.get("tracks").size(); i++){
                JsonNode trackNode = jsonNode.get("tracks").get(i);

                Track artist_top_track = new Track();

                artist_top_track.setId(trackNode.get("id").asText());
                artist_top_track.setName(trackNode.get("name").asText());
                artist_top_track.setImageSource(trackNode.get("album").get("images").get(0).get("url").asText());
                artist_top_track.setDurationMs(trackNode.get("duration_ms").asInt());
                artist_top_track.setPreview_url(trackNode.get("preview_url").asText());

                Album album = new Album();
                album.setId(trackNode.get("album").get("id").asText());
                album.setName(trackNode.get("album").get("name").asText());

                artist_top_track.setAlbum(album);

                artist_top_tracks.add(artist_top_track);
            }
            return artist_top_tracks;
        }
        throw new ArtistNotFoundException();
    }

    public List<Artist> getArtistRelatedArtists(String artist_id) throws IOException, InterruptedException {
        String requestUrl = "https://api.spotify.com/v1/artists/" + artist_id + "/related-artists";

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

            List<Artist> relatedArtists = new ArrayList<>();

            for(int i = 0; i < jsonNode.get("artists").size(); i++){
                JsonNode artistNode = jsonNode.get("artists").get(i);

                Artist artist = new Artist();

                artist.setId(artistNode.get("id").asText());
                artist.setName(artistNode.get("name").asText());
                try{
                    artist.setImageSource(artistNode.get("images").get(0).get("url").asText());
                } catch(NullPointerException ignored){

                }
                relatedArtists.add(artist);
            }
            return relatedArtists;
        }
        throw new ArtistNotFoundException();

    }

    public List<Album> getArtistAlbums(String artist_id, int limit) throws IOException, InterruptedException{
        String requestUrl = "https://api.spotify.com/v1/artists/" + artist_id + "/albums";
        String requestBody = "limit=" + limit;

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

            List<Album> artistAlbums = new ArrayList<>();

            for(int i = 0; i < jsonNode.get("items").size(); i++){
                JsonNode albumNode = jsonNode.get("items").get(i);
                Album album = new Album();

                album.setId(albumNode.get("id").asText());
                album.setName(albumNode.get("name").asText());
                album.setImageSource(albumNode.get("images").get(0).get("url").asText());
                if(albumNode.get("album_type").asText().equals("single") && albumNode.get("total_tracks").asInt() > 1){
                    album.setAlbum_type("EP");
                } else{
                    album.setAlbum_type(albumNode.get("album_type").asText());
                }

                artistAlbums.add(album);
            }
            return artistAlbums;
        }
        throw new ArtistNotFoundException();
    }
}
