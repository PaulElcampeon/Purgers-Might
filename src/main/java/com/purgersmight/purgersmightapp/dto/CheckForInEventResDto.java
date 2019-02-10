package com.purgersmight.purgersmightapp.dto;

import com.purgersmight.purgersmightapp.models.PvpEvent;
import lombok.Data;

@Data
public class CheckForInEventResDto {

    private boolean inEvent;
    private PvpEvent pvpEvent;

    public CheckForInEventResDto() {}

    public CheckForInEventResDto(boolean inEvent) {
        this.inEvent = inEvent;
    }

    public CheckForInEventResDto(boolean inEvent, PvpEvent pvpEvent) {
        this.inEvent = inEvent;
        this.pvpEvent = pvpEvent;
    }
}
