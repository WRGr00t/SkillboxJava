package com.example.blogapp.entity;

import java.util.stream.Stream;

public enum Status {
    NEW("new"),
    ACCEPTED("accepted"),
    DECLINED("declined");

    public String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Status of(String status) {
        return Stream.of(Status.values())
                .filter(s -> s.getStatus().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
