package com.igor.service;


import com.igor.entity.MainUser;
import com.igor.entity.User;
import com.igor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User loadMainUserByUsername(String username) {

        if (userRepository.findUserByUsername(username) != null) {
            return new MainUser(userRepository.findUserByUsername(username));
        } else {
            return null;
        }

    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User loadUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }


    public final boolean exists(User u) {

        u = loadUserByUsername(u.getUsername());
        return u != null;

    }


}
