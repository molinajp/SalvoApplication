package com.codeoftheweb.salvo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PlayerDto {

    private long id;

    private String email;

    public PlayerDto() {
    }

    public PlayerDto(long id, String email) {
        this.id = id;
        this.email = email;
    }

}
