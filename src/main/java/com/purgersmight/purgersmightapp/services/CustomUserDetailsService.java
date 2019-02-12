package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.config.CustomUserDetails;
import com.purgersmight.purgersmightapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.log(Level.WARNING, String.format("User with username %s tried to log in", username));

        Optional<User> user = userService.getUserByUsername(username);

        if (!user.isPresent()) {

            logger.log(Level.INFO, String.format("User with username %s failed to log in", username));

            throw new UsernameNotFoundException("User not found by name: " + username);
        }
//        try {
//
//            Optional<user> = userService.getUserByUsername(username);
//
//        } catch (NoSuchElementException n){
//
//            user = new User("1", "1");
//        }

        return new CustomUserDetails(user.get());
    }
}
