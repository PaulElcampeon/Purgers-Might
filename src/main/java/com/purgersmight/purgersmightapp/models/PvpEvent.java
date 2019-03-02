package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.enums.EventType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PVPEVENT")
public class PvpEvent extends AbstractEvent {

    public PvpEvent() {
        super();
    }

    public PvpEvent(PvpEventBuilder builder) {
        super();
        setEventId(builder.eventId);
        setEventType(builder.eventType);
        setEnded(builder.ended);
        setWhosTurn(builder.whosTurn);
        setPlayer1(builder.player1);
        setPlayer2(builder.player2);
        setTimestamp(builder.timestamp);
        setStartTime(builder.startTime);
    }

    public static class PvpEventBuilder {
        private String eventId;
        private EventType eventType;
        private String whosTurn;
        private boolean ended;
        private Avatar player1;
        private Avatar player2;
        private long timestamp;
        private long startTime;

        public PvpEventBuilder setEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public PvpEventBuilder setEventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public PvpEventBuilder setWhosTurn(String whosTurn) {
            this.whosTurn = whosTurn;
            return this;
        }

        public PvpEventBuilder setEnded(boolean ended) {
            this.ended = ended;
            return this;
        }

        public PvpEventBuilder setPlayer1(Avatar player1) {
            this.player1 = player1;
            return this;
        }

        public PvpEventBuilder setPlayer2(Avatar player2) {
            this.player2 = player2;
            return this;
        }

        public PvpEventBuilder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public PvpEventBuilder setStartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public PvpEvent build() {
            return new PvpEvent(this);
        }
    }
}
