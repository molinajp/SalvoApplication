package com.codeoftheweb.salvo.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;


@Entity
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Date date;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    Set<GamePlayer> gamePlayers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "gameId", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    Set<Score> scores = new LinkedHashSet<>();

    public Game() {
    }

    public Game(Date date) {
        this.date = date;
    }


}
