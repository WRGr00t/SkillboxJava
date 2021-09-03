package com.example.blogapp.repository;

import com.example.blogapp.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepo extends CrudRepository<Tag, Integer> {

    List<Tag> findAll();

    List<Tag> findAllByName (String name);
}
