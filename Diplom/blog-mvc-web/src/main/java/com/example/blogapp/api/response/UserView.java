package com.example.blogapp.api.response;

import com.example.blogapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserView {
    Integer id;
    String name;

    public UserView(User user) {
        this.id = user.getId();
        this.name = user.getUsername();
    }

}
