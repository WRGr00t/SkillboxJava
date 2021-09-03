package com.example.blogapp.api.response;

import com.example.blogapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserViewComment {
    private int id;
    private String name;
    private String photo;

    public UserViewComment(User user) {
        this.id = user.getId();
        this.name = user.getUsername();
        this.photo = user.getPhoto();
    }
}
