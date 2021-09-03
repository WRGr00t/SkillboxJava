package com.example.blogapp.service;

import com.example.blogapp.entity.User;
import com.example.blogapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserService{//} implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }*/

    public Optional<User> loadUserById(Integer id) {
        return userRepo.findById(id);
    }
}
