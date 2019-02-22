package com.purgersmight.purgersmightapp.repositories;

import com.purgersmight.purgersmightapp.models.Spell;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpellRepository extends MongoRepository<Spell, String> {

    public Optional<Spell> findByName(final String name);

}
