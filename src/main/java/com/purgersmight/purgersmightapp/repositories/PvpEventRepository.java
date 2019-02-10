package com.purgersmight.purgersmightapp.repositories;

import com.purgersmight.purgersmightapp.models.PvpEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Scope(value = "singleton")
public interface PvpEventRepository extends MongoRepository<PvpEvent,String> {

    public Optional<PvpEvent> findByEventId(String eventId);
}
