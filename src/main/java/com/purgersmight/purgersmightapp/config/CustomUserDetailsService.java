package com.purgersmight.purgersmightapp.config;

import org.springframework.http.HttpEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        RestTemplate template = new RestTemplate();
//
//        HttpEntity<User> getResponse =
//                template.getForEntity("http://localhost:8080/user/{"+username+"}",User.class);
//        User user = getResponse.getBody();

//        return new CustomUserDetails(user);
        return null;
    }
}
