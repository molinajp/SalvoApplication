package com.codeoftheweb.salvo.services;

import com.codeoftheweb.salvo.dto.GameDto;
import com.codeoftheweb.salvo.dto.GameViewDto;
import com.codeoftheweb.salvo.dto.PlayerDto;
import com.codeoftheweb.salvo.models.*;

public interface GameService {

    PlayerDto makePlayerDto(Player player);

    GameDto makeGameDto(Game game);

    GameViewDto makeGameViewDto(GamePlayer gamePlayer);
}
