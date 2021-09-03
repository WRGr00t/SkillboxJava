package com.example.blogapp.api.response;

import com.example.blogapp.entity.PostComments;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.ZoneId;

@Getter
@AllArgsConstructor
public class CommentView {
    private int id;
    private long timestamp;
    private String text;
    private UserViewComment user;

    public CommentView(PostComments comment) {
        this.id = comment.getId();
        this.timestamp = comment.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000;
        this.text = comment.getText();
        this.user = new UserViewComment(comment.getUser());
    }
}
