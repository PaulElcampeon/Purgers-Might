package com.purgersmight.purgersmightapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BATTLE_STATS")
@AllArgsConstructor
@Data
public class BattleStatistics {

    @Id
    private String username;
    private int level;
    private int victories;
    private int defeats;
    private String imageUrl;

    public BattleStatistics() {
    }

    public BattleStatistics(String username) {
        this.username = username;
    }

    public BattleStatistics(String username, String imageUrl) {
        this.username = username;
        this.imageUrl = imageUrl;
    }
}
