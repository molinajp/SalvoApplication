package com.codeoftheweb.salvo.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;
import java.util.Set;


@Entity
@Data
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Ship> ships;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Salvo> salvoes;

    private Date date;

    public GamePlayer() {
    }

    public GamePlayer(Game game, Player player, Date date) {
        this.game = game;
        this.player = player;
        this.date = date;
    }

    public Optional<Score> getScore() {
        return this.getPlayer().getScore(this.getGame());
    }
}