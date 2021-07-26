package com.codeoftheweb.salvo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class SalvoDto {

    private int turn;

    private long player;

    private List<String> locations;

    public SalvoDto() {
    }

    public SalvoDto(int turn, long player, List<String> locations) {
        this.turn = turn;
        this.player = player;
        this.locations = locations;
    }

}
