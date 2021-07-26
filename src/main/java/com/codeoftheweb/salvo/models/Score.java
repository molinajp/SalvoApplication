package com.codeoftheweb.salvo.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @EqualsAndHashCode.Exclude
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game gameId;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player playerId;

    private double score;

    private Date finishDate;

    public Score() {
    }

    public Score(Game gameId, Player playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public Score(Game gameId, Player playerId, double score, Date finishDate) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.score = score;
        this.finishDate = finishDate;
    }

}
