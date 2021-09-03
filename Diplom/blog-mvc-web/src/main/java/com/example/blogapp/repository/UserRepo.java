package com.example.blogapp.repository;

import com.example.blogapp.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    User findUserByUsername(@Param("name") String name);

    User findUserByEmail(@Param("email") String email);

    User findUserById(@Param("id") int id);

    boolean existsUserByEmail(@Param("email") String email);
}
