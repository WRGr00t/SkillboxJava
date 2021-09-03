package com.example.blogapp.controller;

import com.example.blogapp.api.response.InitResponse;
import com.example.blogapp.entity.GlobalSetting;
import com.example.blogapp.entity.Post;
import com.example.blogapp.entity.Status;
import com.example.blogapp.entity.User;
import com.example.blogapp.repository.PostRepo;
import com.example.blogapp.repository.SettingsRepo;
import com.example.blogapp.repository.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class DefaultController {
    private final InitResponse initResponse;
    private final UserRepo userRepo;
    private final SettingsRepo settingsRepo;
    private final PostRepo postRepo;

    public DefaultController(InitResponse initResponse,
                             UserRepo userRepo,
                             SettingsRepo settingsRepo,
                             PostRepo postRepo) {
        this.initResponse = initResponse;
        this.userRepo = userRepo;
        this.settingsRepo = settingsRepo;
        this.postRepo = postRepo;
    }

    @GetMapping
    public String start() {
        return "index";
    }
}
