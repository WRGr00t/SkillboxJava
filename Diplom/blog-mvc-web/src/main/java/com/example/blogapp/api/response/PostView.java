package com.example.blogapp.api.response;

import com.example.blogapp.entity.Post;
import com.example.blogapp.entity.Tag;
import com.example.blogapp.entity.VoteValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PostView {
    private int id;
    private long timestamp;
    private boolean active;
    private UserView user;
    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private List<CommentView> comments;
    private ArrayList<String> tags;

    public PostView(Post post) {
        this.id = post.getId();
        this.timestamp = post.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000;
        this.active = post.getIsActive() > 0;
        this.user = new UserView(post.getUser());
        this.title = post.getTitle();
        this.text = post.getText();
        this.likeCount = post.getVoteCount(VoteValue.LIKE);
        this.dislikeCount = post.getVoteCount(VoteValue.DISLIKE);
        this.viewCount = post.getViewCount();
        this.comments = post.getComments().stream()
                .map(com -> new CommentView(com))
                .collect(Collectors.toList());
        this.tags = (ArrayList<String>) post.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }
/*
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("id = %d%n time = %tB %<te,  %<tY  %<tT %<Tp%n active = %b%n", getId(), getTimestamp(), isActive()))
                .append(String.format("user = %d %s%n", getUser().getId(), getUser().getName()))
                .append(String.format("Title = %s%n text = %s%n", getTitle(), getText()))
                .append(String.format("counts: like - %d dislike - %d view - %d%n", getLikeCount(), getDislikeCount(), getViewCount()))
                .append(String.format("Comment : %s%n", printComments((ArrayList<CommentView>) getComments())))
                .append(String.format("Tags : %s%n", tags.toString()));
        return stringBuilder.toString();
    }

    private String printComments(ArrayList<CommentView> commentViews) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CommentView commentView : commentViews) {
            stringBuilder.append(String.format("id = %d%n", commentView.getId()))
                    .append(String.format("time = %tB %<te,  %<tY  %<tT %<Tp%n", commentView.getTimestamp()))
                    .append(String.format("Text = %s%n", commentView.getText()))
                    .append(String.format("User: \n id=%d%nname %s%nphoto = %s%n", commentView.getUser().getId(), commentView.getUser().getName(), commentView.getUser().getPhoto()));
        }
        return stringBuilder.toString();
    }

    private String printTags(TagView tagView) {
        StringBuilder stringBuilder = new StringBuilder();
            for (String s : tagView.getTags())
                stringBuilder
                        .append(s)
                        .append("\n");
        return stringBuilder.toString();
    }*/
}
