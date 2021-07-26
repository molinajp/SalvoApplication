package com.codeoftheweb.salvo.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String userName;

    private String password;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    Set<GamePlayer> gamePlayers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "playerId", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    Set<Score> scores = new LinkedHashSet<>();

    public Player() {
    }

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Optional<Score> getScore(Game game) {
        return this.getScores().stream().filter
                (score -> score.getGameId().getId() == game.getId()).findFirst();
    }
}
