package com.example.blogapp.entity;

import com.example.blogapp.api.request.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "is_moderator", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isModerator;

    @Column(name = "reg_time", nullable = false)
    private LocalDateTime regTime;

    @Column(name = "name", nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "code", referencedColumnName = "code")
    private CaptchaCode code;

    @Column(columnDefinition="TEXT")
    private String photo;

    public User(@NotNull boolean isModerator,
                LocalDateTime regTime,
                String username,
                String email,
                String password,
                CaptchaCode code,
                String photo) {
        this.isModerator = isModerator;
        this.regTime = regTime;
        this.username = username;
        this.email = email;
        this.password = password;
        this.code = code;
        this.photo = photo;
    }

    public User(RegisterRequest userRequest) {
        this.isModerator = false;
        this.regTime = LocalDateTime.now();
        this.username = userRequest.getName();
        this.email = userRequest.getEmail();
        this.password = userRequest.getPassword();
    }
}
