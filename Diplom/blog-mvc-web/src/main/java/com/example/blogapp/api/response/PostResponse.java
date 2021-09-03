package com.example.blogapp.api.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PostResponse {
    int count;
    ArrayList<PostForResponse> posts;

    public PostResponse(int count, ArrayList<PostForResponse> posts) {
        this.count = count;
        this.posts = posts;
    }
}
