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
        String requestBody = "q=" + query +
                "&type=artist";

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
            if(jsonNode.get("artists").get("total").asInt() == 0){
                throw new ArtistNotFoundException();
            }
            
            JsonNode artistNode = jsonNode.get("artists").get("items");

            List<Artist> artists = new ArrayList<>();

            for(int i = 0; i < artistNode.size(); i++){
                Artist artist = new Artist();

                artist.setId(artistNode.get(i).get("id").asText());
                try{
                    artist.setImageSource(artistNode.get(i).get("images").get(2).get("url").asText());
                } catch (NullPointerException ignored){

                }
                artist.setName(artistNode.get(i).get("name").asText());
                artist.setFollowers(artistNode.get(i).get("followers").get("total").asInt());
                artist.setGenres(new ArrayList<>());
                for (int j = 0; j < artistNode.get(i).get("genres").size(); j++) {
                    artist.addGenre(artistNode.get(i).get("genres").elements().next().asText());
                }
                
                artists.add(artist);
            }
            return artists;
        }
        return null;
    }
}
