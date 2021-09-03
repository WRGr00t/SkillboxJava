package com.example.blogapp.entity;

//import org.springframework.security.core.GrantedAuthority;

public enum Role {//implements GrantedAuthority {
    USER,
    MODERATOR,
    ADMIN;

   /* @Override
    public String getAuthority() {
        return name();
    }*/
}
