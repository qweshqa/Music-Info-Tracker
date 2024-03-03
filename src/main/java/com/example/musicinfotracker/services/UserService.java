package com.example.musicinfotracker.services;

import com.example.musicinfotracker.model.UserEntity;
import com.example.musicinfotracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findByUsername(String username){
        Optional<UserEntity> user = userRepository.findByUsername(username);

        return user.orElse(null);
    }
    @Transactional
    public void save(UserEntity userEntity){
        userRepository.save(userEntity);
    }
}
