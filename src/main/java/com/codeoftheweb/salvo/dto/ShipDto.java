package com.codeoftheweb.salvo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class ShipDto {

    private String type;

    private List<String> locations;

    public ShipDto() {
    }

    public ShipDto(String type, List<String> locations) {
        this.type = type;
        this.locations = locations;
    }

}
