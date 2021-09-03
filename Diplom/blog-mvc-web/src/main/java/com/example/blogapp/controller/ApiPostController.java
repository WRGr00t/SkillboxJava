package com.example.blogapp.controller;

import com.example.blogapp.api.response.PostForResponse;
import com.example.blogapp.api.response.PostResponse;
import com.example.blogapp.api.response.PostView;
import com.example.blogapp.entity.Post;
import com.example.blogapp.entity.Status;
import com.example.blogapp.entity.Tag;
import com.example.blogapp.exceptions.ResourceNotFoundException;
import com.example.blogapp.repository.PostRepo;
import com.example.blogapp.repository.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ApiPostController {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private TagRepo tagRepo;

    @GetMapping(path = "/api/post", params = {"offset", "limit"})
    public ResponseEntity<PostResponse> getPosts(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                 @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                 @RequestParam(defaultValue = "recent") String mode) {
        Sort sort = switchSort(mode);
        int pageNo = offset / limit;
        Page<Post> page;
        Pageable pageable;
        if (mode.equals("best")) {
            pageable = PageRequest.of(pageNo, limit, Sort.unsorted());
            page = postRepo.showPostByLikes(pageable);

        } else if (mode.equals("popular")) {
            pageable = PageRequest.of(pageNo, limit, Sort.unsorted());
            page = postRepo.showPostByComments(pageable);
        } else {
            pageable = PageRequest.of(pageNo, limit, sort);
            page = postRepo.findAllActual(pageable);
        }
        ArrayList<PostForResponse> responses = showResponse(page.getContent());
        PostResponse postResponse = new PostResponse((int) page.getTotalElements(), responses);
        return ResponseEntity.status(HttpStatus.OK).body(postResponse);
    }

    @GetMapping("/api/post/search")
    public ResponseEntity<PostResponse> searchPosts(@RequestParam(defaultValue = "0") int offset,
                                                    @RequestParam(defaultValue = "10") int limit,
                                                    @RequestParam String query) {

        int pageNo = offset / limit;
        Pageable pageable = PageRequest.of(pageNo, limit, switchSort("recent"));
        Page<Post> page = postRepo.findAllByTitleContainingOrTextContaining(pageable, query, query);
        return showActualPosts(page.getContent());
    }

    @GetMapping("/api/post/byDate")
    public ResponseEntity<PostResponse> getPostsByDate(@RequestParam(defaultValue = "0") int offset,
                                                       @RequestParam(defaultValue = "10") int limit,
                                                       @RequestParam String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        int pageNo = offset / limit;
        Pageable pageable = PageRequest.of(pageNo, limit, Sort.unsorted());
        Page<Post> page = postRepo.findAllActualByTimeBetween(pageable,
                localDate.atTime(LocalTime.MIN),
                localDate.atTime(LocalTime.MAX));
        ArrayList<PostForResponse> responses = showResponse(page.getContent());
        PostResponse postResponse = new PostResponse((int) page.getTotalElements(), responses);
        return ResponseEntity.status(HttpStatus.OK).body(postResponse);
    }

    @GetMapping("/api/post/byTag")
    public ResponseEntity<PostResponse> getPostsByTag(@RequestParam(defaultValue = "0") int offset,
                                                      @RequestParam(defaultValue = "10") int limit,
                                                      @RequestParam(defaultValue = "") String tag) {
        int pageNo = offset / limit;
        Pageable pageable = PageRequest.of(pageNo, limit, Sort.unsorted());
        ArrayList<Tag> tags = (ArrayList<Tag>) tagRepo.findAllByName(tag);
        Page<Post> page = postRepo.findAllByTagsIn(pageable, tags);
        return showActualPosts(page.getContent());
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity<PostView> showPostsById(@PathVariable int id) {
        List<Post> post = postRepo.findAllById(id);
        if (post.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        Optional<Post> optional = post.stream().findFirst();
        return showActualPost(optional.get());

    }

    private Sort switchSort(String mode) {
        Sort sort;
        switch (mode) {
            case ("early"): {
                sort = Sort.by("time");
                break;
            }
            /*case("best")  : {
                sort = Sort.by("");
                break;
            }
            case("popular") : {
                sort = Sort.by("p.comments");
                break;
            }*/
            default: {
                sort = Sort.by("time").descending();
                break;
            }
        }
        return sort;
    }

    private ResponseEntity<PostResponse> showActualPosts(List<Post> page) {
        ArrayList<PostForResponse> actualPosts = showActualResponse(page);
        PostResponse postResponse = new PostResponse(actualPosts.size(), actualPosts);
        return ResponseEntity.status(HttpStatus.OK).body(postResponse);
    }

    private ArrayList<PostForResponse> showActualResponse(List<Post> page) {
        return (ArrayList<PostForResponse>) page.stream()
                .filter(post -> post.getTime().isBefore(LocalDateTime.now()))
                .filter(post -> post.getIsActive() > 0)
                .filter(post -> post.getModerationStatus().equals(Status.ACCEPTED))
                .map(PostForResponse::new)
                .collect(Collectors.toList());
    }

    private ResponseEntity<PostView> showActualPost(Post post) {
        if (post.getTime().isBefore(LocalDateTime.now()) && (post.getIsActive() > 0) && post.getModerationStatus().equals(Status.ACCEPTED)) {
            PostView postView = new PostView(post);
            return ResponseEntity.status(HttpStatus.OK).body(postView);
        } else {
            return null;
        }
    }

    private ArrayList<PostForResponse> showResponse(List<Post> page) {
        return (ArrayList<PostForResponse>) page.stream()
                .map(PostForResponse::new)
                .collect(Collectors.toList());
    }
}
