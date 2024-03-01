package com.example.musicinfotracker.utils;

import com.example.musicinfotracker.model.UserEntity;
import com.example.musicinfotracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserEntity.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEntity userEntity = (UserEntity)target;

        if(userService.findByUsername(userEntity.getUsername()) != null){
            errors.rejectValue("username", "", "Username already exists");
        }
    }
}
