package com.purgersmight.purgersmightapp.repositories;

import com.purgersmight.purgersmightapp.models.Avatar;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Scope(value = "singleton")
public interface AvatarRepository extends MongoRepository<Avatar,String> {

    public Optional<Avatar> findByUsername(String username);
}
