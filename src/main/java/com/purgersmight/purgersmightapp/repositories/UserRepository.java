package com.purgersmight.purgersmightapp.repositories;

import com.purgersmight.purgersmightapp.models.User;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Scope(value = "singleton")
public interface UserRepository extends MongoRepository<User,String> {

    public Optional<User> findByUsername(final String username);
}
