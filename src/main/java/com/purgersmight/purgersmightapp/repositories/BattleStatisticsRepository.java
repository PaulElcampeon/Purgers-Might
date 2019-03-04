package com.purgersmight.purgersmightapp.repositories;

import com.purgersmight.purgersmightapp.models.BattleStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BattleStatisticsRepository extends MongoRepository<BattleStatistics,String> {

    public Optional<BattleStatistics> findByUsername(String username);

}
