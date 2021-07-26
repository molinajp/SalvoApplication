package com.codeoftheweb.salvo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Data
public class SelfAndOppDto {

    private int turn;

    private List<String> hitLocations;

    private DamagesDto damages;

    private int missed;

    public SelfAndOppDto() {
    }

    public SelfAndOppDto(int turn, List<String> hitLocations, DamagesDto damages, int missed) {
        this.turn = turn;
        this.hitLocations = hitLocations;
        this.damages = damages;
        this.missed = missed;
    }

}
