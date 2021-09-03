package com.example.blogapp.controller;

import com.example.blogapp.api.response.CaptchaResponse;
import com.example.blogapp.api.response.CheckResponse;
import com.example.blogapp.api.request.RegisterRequest;
import com.example.blogapp.entity.CaptchaCode;
import com.example.blogapp.entity.User;
import com.example.blogapp.repository.UserRepo;
import com.example.blogapp.service.CaptchaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
public class ApiAuthController {
    private CheckResponse authResponse;
    private CaptchaService captchaService;
    private UserRepo userRepo;

    public ApiAuthController(CheckResponse authResponse, CaptchaService captchaService, UserRepo userRepo) {
        this.authResponse = authResponse;
        this.captchaService = captchaService;
        this.userRepo = userRepo;
    }

    @GetMapping("/check")
    public CheckResponse check(){
        return new CheckResponse();
    }

   @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> getCaptcha() {
        //captchaService.deleteByTime();
        return new ResponseEntity<>(captchaService.generateCaptchaResponse(), HttpStatus.OK);
    }

    /*@PostMapping("/register")
    public ResponseEntity<RegisterRequest> register(@Valid @RequestBody RegisterRequest request) {
        User user = new User(request);
        CaptchaCode code = new CaptchaCode(request);
        user.setCode(code);
        code.setUser(user);
        if (!userRepo.existsUserByEmail(user.getEmail())) {
            userRepo.save(user);
            captchaService.saveCaptcha(code);
        }
    }*/
}
