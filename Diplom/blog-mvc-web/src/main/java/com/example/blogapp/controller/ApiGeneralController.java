package com.example.blogapp.controller;

import com.example.blogapp.api.response.CalendarResponse;
import com.example.blogapp.api.response.InitResponse;
import com.example.blogapp.api.response.SettingsResponse;
import com.example.blogapp.api.response.TagResponse;
import com.example.blogapp.entity.Post;
import com.example.blogapp.entity.Tag;
import com.example.blogapp.entity.TagWeight;
import com.example.blogapp.repository.PostRepo;
import com.example.blogapp.repository.TagRepo;
import com.example.blogapp.service.SettingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class ApiGeneralController {
    private InitResponse initResponse;
    private SettingsService settingsService;
    private PostRepo postRepo;
    private TagRepo tagRepo;

    @GetMapping("/settings")
    private SettingsResponse settings() {
        return settingsService.getGlobalSettings();
    }

    @GetMapping("/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/tag")
    public ResponseEntity<TagResponse> getAllTags() {
        return new ResponseEntity<>(getTags(), HttpStatus.OK);
    }

    @GetMapping("/calendar")
    private CalendarResponse getCalendar() {
        return new CalendarResponse(postRepo);
    }

    private TagResponse getTags() {
        TagResponse tagResponse = new TagResponse();
        Set<TagWeight> set = new HashSet<>();
        int N = postRepo.findAll().size();
        if (!getTagSet().isEmpty()) {
            for (Tag tag : getTagSet()) {
                float w = (float)getTagCount(tag) / N / getMax();
                TagWeight tw = new TagWeight(tag.getName(), w);
                set.add(tw);
            }
        }
        tagResponse.setTags(set);
        return tagResponse;
    }

    private int getTagCount(Tag tag) {
        int count = 0;
        for (Post post : postRepo.findAll()) {
            if (post.getTags().contains(tag)) {
                count++;
            }
        }
        return count;
    }

    private HashMap<String, Float> getTagW() {
        HashMap<String, Float> set = new HashMap<>();
        int postCount = postRepo.findAll().size();
        List<Tag> tags = tagRepo.findAll();
        if (postCount > 0) {
            for (Tag tag : tags) {
                float tagW = (float)getTagCount(tag) / postCount;
                set.put(tag.getName(), tagW);
            }
        }
        return set;
    }

    private float getMax() {
        Optional<Float> max = getTagW().values().stream()
                .filter(x -> x !=0)
                .max(Float::compareTo);
        return max.orElse(1.0F);
    }

    public Set<Tag> getTagSet() {
        Set<Tag> set;
        if (postRepo.findAll().isEmpty()) {
            set = new HashSet<>();
        } else {
            set = postRepo.findAll().stream()
                    .map(Post::getTags)
                    .filter(t -> t !=null && !t.isEmpty())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        }
        return set;
    }

}
