package com.example.musicinfotracker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class MusicInfoTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicInfoTrackerApplication.class, args);
    }

    @Bean
    public static String accessToken(@Value("${CLIENT_ID}") String client_id,
                              @Value("${CLIENT_SECRET}") String client_secret) throws IOException, InterruptedException {

        String accessToken = "";

        String tokenRequestUrl = "https://accounts.spotify.com/api/token";
        String tokenRequestBody = "grant_type=client_credentials" +
                "&client_id=" + client_id +
                "&client_secret=" + client_secret;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(tokenRequestUrl))
                .header("Content-type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(tokenRequestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());
                accessToken = jsonNode.get("access_token").asText();
                System.out.println("Access Token: " + accessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: " + response.statusCode());
        }
        return accessToken;
    }
}
