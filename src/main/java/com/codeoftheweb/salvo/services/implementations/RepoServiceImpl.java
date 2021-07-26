package com.codeoftheweb.salvo.services.implementations;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.*;
import com.codeoftheweb.salvo.services.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepoServiceImpl implements RepoService {

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    ShipRepository shipRepository;

    @Autowired
    SalvoRepository salvoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Optional<GamePlayer> getGamePlayer(Long gpId) {
        return gamePlayerRepository.findById(gpId);
    }

    public boolean existsGamePlayer(Long gamePlayerId){
        return gamePlayerRepository.existsById(gamePlayerId);
    }

    public void saveGamePlayer(GamePlayer gamePlayer){
        gamePlayerRepository.save(gamePlayer);
    }

    public Optional<Player> getPlayer(String email){
        return playerRepository.findByUserName(email);
    }

    public void savePlayer(Player player){
        playerRepository.save(player);
    }

    public void saveSalvo(Salvo salvo){
        salvoRepository.save(salvo);
    }

    public void saveShip(Ship ship){
        shipRepository.save(ship);
    }

    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    public boolean existsGame(Long gameId){
        return gameRepository.existsById(gameId);
    }

    public Game getGame(Long gameId){
        return gameRepository.getOne(gameId);
    }

    public void saveGame(Game game){
        gameRepository.save(game);
    }

    public String encode(String password){
        return passwordEncoder.encode(password);
    }
}
