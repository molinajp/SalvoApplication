package com.codeoftheweb.salvo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class ScoreDto {

    private long player;

    private double score;

    private Date finishDate;

    public ScoreDto() {
    }

    public ScoreDto(long player, double score, Date finishDate) {
        this.player = player;
        this.score = score;
        this.finishDate = finishDate;
    }

}
