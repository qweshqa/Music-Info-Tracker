package com.example.musicinfotracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {

    private String accessToken;

    @Autowired
    public PlaylistService(@Qualifier("accessToken") String accessToken) { this.accessToken = accessToken; }


}
