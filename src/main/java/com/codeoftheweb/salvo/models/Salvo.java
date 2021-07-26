package com.codeoftheweb.salvo.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    private int turn;

    @ElementCollection
    @Column(name = "locations")
    private List<String> salvoLocations;

    public Salvo() {
    }

    public Salvo(int turn, List<String> locations, GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        this.turn = turn;
        this.salvoLocations = locations;
    }

}
