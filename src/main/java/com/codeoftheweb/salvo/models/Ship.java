package com.codeoftheweb.salvo.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    private String type;

    @ElementCollection
    @Column(name = "locations")
    private List<String> shipLocations;

    public Ship() {
    }

    public Ship(String type, List<String> shipLocations, GamePlayer gp) {
        this.type = type;
        this.shipLocations = shipLocations;
        this.gamePlayer = gp;
    }

}
