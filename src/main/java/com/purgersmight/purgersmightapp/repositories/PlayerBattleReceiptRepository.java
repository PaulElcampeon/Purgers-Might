package com.purgersmight.purgersmightapp.repositories;

import com.purgersmight.purgersmightapp.models.PlayerBattleReceipts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerBattleReceiptRepository extends MongoRepository<PlayerBattleReceipts, String> {

    public Optional<PlayerBattleReceipts> findByUsername(String username);
}
