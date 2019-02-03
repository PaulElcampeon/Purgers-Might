package com.purgersmight.purgersmightapp.repositories;

import com.purgersmight.purgersmightapp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    public User findByUsername(String username);
}
