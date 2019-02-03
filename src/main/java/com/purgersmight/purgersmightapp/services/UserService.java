package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addUser(User newUser){
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        userRepository.insert(newUser);
    }

    public User getUserByUsername(final String username){
        return userRepository.findByUsername(username);
    }

    public void removeUser(final User user){
        userRepository.delete(user);
    }

    public void removeUserById(final String username){
        userRepository.deleteById(username);
    }

    public void updateUser(final User user){
        userRepository.save(user);
    }
}
