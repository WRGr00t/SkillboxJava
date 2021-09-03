package com.example.blogapp.entity;

import com.example.blogapp.api.request.RegisterRequest;
import com.example.blogapp.api.response.CaptchaResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "captcha_codes")
@Getter @Setter
public class CaptchaCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(columnDefinition="TINYTEXT", nullable = false)
    private String code;

    @Column(name = "secret_code", columnDefinition="TINYTEXT", nullable = false)
    private String secretCode;

    @OneToOne(mappedBy = "code")
    private User user;

    public CaptchaCode() {
    }

    public CaptchaCode(CaptchaResponse captchaResponse) {
        this.time = LocalDateTime.now();
        this.code = captchaResponse.getSecret();
    }

    public CaptchaCode(RegisterRequest request) {
        this.time = LocalDateTime.now();
        this.code = request.getCaptcha();
        this.secretCode = request.getCaptchaSecret();
    }
}
