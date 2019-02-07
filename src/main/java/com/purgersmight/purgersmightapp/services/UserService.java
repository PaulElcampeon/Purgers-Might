package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Scope(value = "singleton")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Logger logger = Logger.getLogger(UserService.class.getName());

    public void addUser(User newUser){
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        userRepository.insert(newUser);
        logger.log(Level.INFO, String.format("%s has just created an account",newUser.getUsername()));
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

    public void removeAllUsers(){
        userRepository.deleteAll();
    }
}
