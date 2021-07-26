package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.util.GameState;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Data
public class GameViewDto {

    private long id;

    private Date created;

    private GameState gameState;

    private Set<GamePlayerDto> gamePlayers;

    private Set<ShipDto> ships;

    private Set<SalvoDto> salvoes;

    private HitsDto hits;

    public GameViewDto() {
    }

    public GameViewDto(long id, Date created, GameState gameState, Set<GamePlayerDto> gamePlayers,
                       Set<ShipDto> ships, Set<SalvoDto> salvoes, HitsDto hits) {
        this.id = id;
        this.created = created;
        this.gameState = gameState;
        this.gamePlayers = gamePlayers;
        this.ships = ships;
        this.salvoes = salvoes;
        this.hits = hits;
    }

}
