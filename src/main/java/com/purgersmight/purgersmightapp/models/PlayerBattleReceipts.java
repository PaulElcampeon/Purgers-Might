package com.purgersmight.purgersmightapp.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "RECEITPS")
public class PlayerBattleReceipts {

    private String username;
    private List<BattleReceipt> battleReceipts = new ArrayList<>();

    public PlayerBattleReceipts() {};

    public PlayerBattleReceipts(String username) {
        this.username = username;
    };
}
