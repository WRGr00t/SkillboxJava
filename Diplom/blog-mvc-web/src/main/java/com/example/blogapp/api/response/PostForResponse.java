package com.example.blogapp.api.response;

import com.example.blogapp.entity.Post;
import com.example.blogapp.entity.VoteValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Jsoup;

import java.time.ZoneId;

@Getter
@AllArgsConstructor
public class PostForResponse {

    long id;
    long timestamp;
    UserView user;
    String title;
    String announce;
    int likeCount;
    int dislikeCount;
    int commentCount;
    int viewCount;

    public PostForResponse(Post post) {
        this.id = post.getId();
        this.timestamp = post.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000;
        this.user = new UserView(post.getUser());
        this.title = post.getTitle();
        this.announce = normalizeText(post.getText());
        this.likeCount = post.getVoteCount(VoteValue.LIKE);
        this.dislikeCount = post.getVoteCount(VoteValue.DISLIKE);
        this.commentCount = post.getComments().size();
        this.viewCount = post.getViewCount();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public UserView getUser() {
        return user;
    }

    public void setUser(UserView user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    private String normalizeText(String string) {
        int n = 150;
        string = Jsoup.parse(string).text();
        StringBuffer strBuffer = new StringBuffer();
        if (string.length() > n) {
            string = string.substring(0, n);
            strBuffer.append(string);
            strBuffer.append("...");
        } else {
            strBuffer.append(string);
        }
        return strBuffer.toString();
    }
}
