package com.codeoftheweb.salvo.dto;

import lombok.Data;

@Data
public class DamagesDto {

    private int carrierHits;

    private int destroyerHits;

    private int patrolboatHits;

    private int submarineHits;

    private int battleshipHits;

    private int carrier;

    private int destroyer;

    private int patrolboat;

    private int submarine;

    private int battleship;

    public DamagesDto() {
    }

    public DamagesDto(int carrierHits, int destroyerHits, int patrolboatHits, int submarineHits,
                      int battleshipHits, int carrier, int destroyer, int patrolboat, int submarine, int battleship) {
        this.carrierHits = carrierHits;
        this.destroyerHits = destroyerHits;
        this.patrolboatHits = patrolboatHits;
        this.submarineHits = submarineHits;
        this.battleshipHits = battleshipHits;
        this.carrier = carrier;
        this.destroyer = destroyer;
        this.patrolboat = patrolboat;
        this.submarine = submarine;
        this.battleship = battleship;
    }

}
