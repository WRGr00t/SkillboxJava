package com.example.blogapp.api.response;

import com.example.blogapp.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class TagView {
    private ArrayList<String> tags;

    public TagView(List<Tag> tagList) {
        tags = (ArrayList<String>) tagList.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }
}
