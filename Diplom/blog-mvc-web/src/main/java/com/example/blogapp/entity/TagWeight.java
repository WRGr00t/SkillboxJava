package com.example.blogapp.entity;

public class TagWeight {
    private String name;
    private float weight;

    public TagWeight() {
    }

    public TagWeight(String name, float weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
