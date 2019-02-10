package com.purgersmight.purgersmightapp.services;

import com.purgersmight.purgersmightapp.config.CustomUserDetails;
import com.purgersmight.purgersmightapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        User user = userService.getUserByUsername(username);
        return new CustomUserDetails(user);
    }
}
