package com.codeoftheweb.salvo.services;

import com.codeoftheweb.salvo.models.*;

import java.util.List;
import java.util.Optional;

public interface RepoService {

    Optional<GamePlayer> getGamePlayer(Long gpId);

    boolean existsGamePlayer(Long gamePlayerId);

    void saveGamePlayer(GamePlayer gamePlayer);

    Optional<Player> getPlayer(String email);

    void savePlayer(Player player);

    void saveSalvo(Salvo salvo);

    void saveShip(Ship ship);

    List<Game> getAllGames();

    boolean existsGame(Long gameId);

    Game getGame(Long gameId);

    void saveGame(Game game);

    String encode(String password);
}
