package com.purgersmight.purgersmightapp.repositories;

import com.purgersmight.purgersmightapp.models.Avatar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends MongoRepository<Avatar,String> {

    public Avatar findByUsername(String username);
}
