package com.example.blogapp.api.response;

import com.example.blogapp.entity.TagWeight;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TagResponse {
    private Set<TagWeight> tags = new HashSet<>();

    public TagResponse() {
    }

    public TagResponse(Set<TagWeight> tags) {
        this.tags = tags;
    }

    public Set<TagWeight> getTags() {
        return tags;
    }

    public void setTags(Set<TagWeight> tags) {
        this.tags = tags;
    }
}
