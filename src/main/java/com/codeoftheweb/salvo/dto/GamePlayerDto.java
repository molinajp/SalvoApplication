package com.codeoftheweb.salvo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class GamePlayerDto {

    private long id;

    private PlayerDto player;

    public GamePlayerDto() {
    }

    public GamePlayerDto(long id, PlayerDto player) {
        this.id = id;
        this.player = player;
    }

}
