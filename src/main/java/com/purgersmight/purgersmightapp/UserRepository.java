package com.purgersmight.purgersmightapp;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

    public User findUserByUsername(String username);
}
