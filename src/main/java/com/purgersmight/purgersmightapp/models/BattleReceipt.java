package com.purgersmight.purgersmightapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BattleReceipt {

    private String winner;
    private String loser;
    private String date;
    private String time;


    public BattleReceipt() {
    }
}
