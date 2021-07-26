package com.codeoftheweb.salvo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Data
public class GameDto {

    private long id;

    private Date created;

    private Set<GamePlayerDto> gamePlayers;

    private Set<ScoreDto> scores;

    public GameDto() {
    }

    public GameDto(long id, Date created, Set<GamePlayerDto> gamePlayers, Set<ScoreDto> scores) {
        this.id = id;
        this.created = created;
        this.gamePlayers = gamePlayers;
        this.scores = scores;
    }

}
