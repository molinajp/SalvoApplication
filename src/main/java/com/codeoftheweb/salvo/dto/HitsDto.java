package com.codeoftheweb.salvo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Data
public class HitsDto {

    private List<Object> self = new ArrayList<>();

    private List<Object> opponent = new ArrayList<>();

    public HitsDto(){}

    public HitsDto(List<Object> self, List<Object> opponent) {
        this.self = self;
        this.opponent = opponent;
    }

}