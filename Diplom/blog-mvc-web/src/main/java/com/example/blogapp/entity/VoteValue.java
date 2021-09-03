package com.example.blogapp.entity;

import java.util.stream.Stream;

public enum VoteValue {
    LIKE(1),
    DISLIKE(-1);

    public int value;

    VoteValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static  VoteValue of(int value) {
        return Stream.of(VoteValue.values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
