package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.enums.EventType;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public abstract class AbstractEvent {

    @Id
    private String eventId;
    private EventType eventType;
    private String whosTurn;
    private boolean ended;
    private Avatar player1;
    private Avatar player2;
    private long timestamp;
    private long startTime;
}
