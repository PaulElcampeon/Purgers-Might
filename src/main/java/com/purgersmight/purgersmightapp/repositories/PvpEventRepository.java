package com.purgersmight.purgersmightapp.repositories;

import com.purgersmight.purgersmightapp.models.PvpEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = "singleton")
public interface EventRepository extends MongoRepository<PvpEvent,String> {

    public PvpEvent findByEventId(String eventId);
}
