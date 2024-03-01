package com.example.musicinfotracker.controllers;

import com.example.musicinfotracker.model.UserEntity;
import com.example.musicinfotracker.services.UserService;
import com.example.musicinfotracker.utils.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public AuthController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") UserEntity userEntity){ return "auth/registration"; }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user") @Valid UserEntity userEntity, BindingResult bindingResult){
        userValidator.validate(userEntity, bindingResult);

        if(bindingResult.hasErrors()) return "redirect:/registration";

        userService.save(userEntity);

        return "redirect:/user";
    }
}
