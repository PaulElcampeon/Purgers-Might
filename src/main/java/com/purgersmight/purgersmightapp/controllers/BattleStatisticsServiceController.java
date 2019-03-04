package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.models.BattleStatistics;
import com.purgersmight.purgersmightapp.services.BattleStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BattleStatisticsServiceController {

    @Autowired
    private BattleStatisticsService battleStatisticsService;

    @RequestMapping(value = "/battle-statistics-service/leaderboard/{skip}", method = RequestMethod.GET)
    public List<BattleStatistics> getLeaderboard(@PathVariable int skip) {

        return battleStatisticsService.getLeaderBoard(skip);
    }
}
