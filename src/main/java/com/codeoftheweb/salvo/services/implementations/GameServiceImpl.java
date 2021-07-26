package com.codeoftheweb.salvo.services.implementations;

import com.codeoftheweb.salvo.dto.*;
import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.*;
import com.codeoftheweb.salvo.services.GameService;
import com.codeoftheweb.salvo.util.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.codeoftheweb.salvo.util.Util.*;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    ScoreRepository scoreRepository;

    public PlayerDto makePlayerDto(Player player) {
        var playerDto = new PlayerDto();
        playerDto.setEmail(player.getUserName());
        playerDto.setId(player.getId());
        return playerDto;
    }

    public GameDto makeGameDto(Game game) {
        var gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setCreated(game.getDate());
        gameDto.setGamePlayers(game.getGamePlayers().stream().map(this::makeGamePlayerDto).collect(Collectors.toSet()));
        gameDto.setScores(game.getGamePlayers().stream().map(this::makeScoreDto).collect(Collectors.toSet()));
        return gameDto;
    }

    public GameViewDto makeGameViewDto(GamePlayer gamePlayer) {
        var gameViewDto = new GameViewDto();
        gameViewDto.setId(gamePlayer.getGame().getId());
        gameViewDto.setCreated(gamePlayer.getGame().getDate());
        gameViewDto.setGameState(decideGameState(gamePlayer));
        gameViewDto.setGamePlayers(gamePlayer.getGame().getGamePlayers().stream().map(this::makeGamePlayerDto)
                .collect(Collectors.toSet()));
        gameViewDto.setShips(gamePlayer.getShips().stream().map(this::makeShipDto).collect(Collectors.toSet()));
        gameViewDto.setSalvoes(gamePlayer.getGame().getGamePlayers().stream().flatMap(
                gp -> gp.getSalvoes().stream().map(this::makeSalvoDto)).collect(Collectors.toSet()));
        var opp = getOpponent(gamePlayer);
        if (opp.isPresent()) {
            gameViewDto.setHits(makeHitsDto(gamePlayer, opp.get()));
        } else {
            gameViewDto.setHits(new HitsDto());
        }
        return gameViewDto;
    }

    private GamePlayerDto makeGamePlayerDto(GamePlayer gamePlayer) {
        var gamePlayerDto = new GamePlayerDto();
        gamePlayerDto.setId(gamePlayer.getId());
        gamePlayerDto.setPlayer(makePlayerDto(gamePlayer.getPlayer()));
        return gamePlayerDto;
    }

    private ScoreDto makeScoreDto(GamePlayer gamePlayer) {
        var scoreDto = new ScoreDto();
        if (gamePlayer.getScore().isEmpty() || gamePlayer.getScore().get().getFinishDate() == null) {
            scoreDto.setScore(0);
        } else {
            scoreDto.setPlayer(gamePlayer.getScore().get().getPlayerId().getId());
            scoreDto.setScore(gamePlayer.getScore().get().getScore());
            scoreDto.setFinishDate(gamePlayer.getScore().get().getFinishDate());
        }
        return scoreDto;
    }

    private GameState decideGameState(GamePlayer gamePlayer) {
        var opp = getOpponent(gamePlayer);
        if (gamePlayer.getShips().size() == 0) {
            return GameState.PLACESHIPS;
        }
        if (gamePlayer.getShips().size() != 0 && (opp.isEmpty() || opp.get().getShips().size() != 5)) {
            return GameState.WAITINGFOROPP;
        }
        var oppHits = getCountHitsDto(gamePlayer, opp.get());
        var selfHits = getCountHitsDto(opp.get(), gamePlayer);
        var shipCantidad = gamePlayer.getShips().stream().mapToLong(ship -> ship.getShipLocations().size()).sum();
        if (gamePlayer.getSalvoes().size() == opp.get().getSalvoes().size()) {
            if (oppHits == shipCantidad && selfHits == shipCantidad) {
                scoreRepository.save(new Score(gamePlayer.getGame(),gamePlayer.getPlayer(),
                        0.5, Date.from(Instant.now())));
                return GameState.TIE;
            } else if (oppHits == shipCantidad) {
                scoreRepository.save(new Score(gamePlayer.getGame(),gamePlayer.getPlayer(),
                        0, Date.from(Instant.now())));
                return GameState.LOST;
            } else if (selfHits == shipCantidad) {
                scoreRepository.save(new Score(gamePlayer.getGame(),gamePlayer.getPlayer(),
                        1, Date.from(Instant.now())));
                return GameState.WON;
            }
        }

        if (gamePlayer.getSalvoes().size() == opp.get().getSalvoes().size() ||
                gamePlayer.getSalvoes().size() - 1 != opp.get().getSalvoes().size()) {
            return GameState.PLAY;
        } else {
            return GameState.WAIT;
        }
    }

    private ShipDto makeShipDto(Ship ship) {
        var shipDto = new ShipDto();
        shipDto.setType(ship.getType());
        shipDto.setLocations(ship.getShipLocations());
        return shipDto;
    }

    private SalvoDto makeSalvoDto(Salvo salvo) {
        var salvoDto = new SalvoDto();
        salvoDto.setTurn(salvo.getTurn());
        salvoDto.setPlayer(salvo.getGamePlayer().getPlayer().getId());
        salvoDto.setLocations(salvo.getSalvoLocations());
        return salvoDto;
    }

    private HitsDto makeHitsDto(GamePlayer gamePlayer, GamePlayer opponentGamePlayer) {
        var hitsDto = new HitsDto();
        int submarineCountSelf = 0;
        int patrolboatCountSelf = 0;
        int destroyerCountSelf = 0;
        int carrierCountSelf = 0;
        int battleshipCountSelf = 0;

        int submarineCountOpp = 0;
        int patrolboatCountOpp = 0;
        int destroyerCountOpp = 0;
        int carrierCountOpp = 0;
        int battleshipCountOpp = 0;
        if (opponentGamePlayer.getSalvoes().size() >= 1) {
            var aux = fromSetToList(opponentGamePlayer.getSalvoes());
            for (Salvo salvo : aux) {
                var dto = makeSelfAndOppDto(salvo, gamePlayer);
                if (salvo.getTurn() != 1) {
                    dto.getDamages().setSubmarine(submarineCountSelf + dto.getDamages().getSubmarineHits());
                    dto.getDamages().setPatrolboat(patrolboatCountSelf + dto.getDamages().getPatrolboatHits());
                    dto.getDamages().setDestroyer(destroyerCountSelf + dto.getDamages().getDestroyerHits());
                    dto.getDamages().setCarrier(carrierCountSelf + dto.getDamages().getCarrierHits());
                    dto.getDamages().setBattleship(battleshipCountSelf + dto.getDamages().getBattleshipHits());
                }
                submarineCountSelf = dto.getDamages().getSubmarine();
                patrolboatCountSelf = dto.getDamages().getPatrolboat();
                destroyerCountSelf = dto.getDamages().getDestroyer();
                carrierCountSelf = dto.getDamages().getCarrier();
                battleshipCountSelf = dto.getDamages().getBattleship();
                hitsDto.getSelf().add(dto);
            }
        }
        if (gamePlayer.getSalvoes().size() >= 1) {
            var aux = fromSetToList(gamePlayer.getSalvoes());
            for (Salvo salvo : aux) {
                var dto = makeSelfAndOppDto(salvo, opponentGamePlayer);
                if (salvo.getTurn() != 1) {
                    dto.getDamages().setSubmarine(submarineCountOpp + dto.getDamages().getSubmarineHits());
                    dto.getDamages().setPatrolboat(patrolboatCountOpp + dto.getDamages().getPatrolboatHits());
                    dto.getDamages().setDestroyer(destroyerCountOpp + dto.getDamages().getDestroyerHits());
                    dto.getDamages().setCarrier(carrierCountOpp + dto.getDamages().getCarrierHits());
                    dto.getDamages().setBattleship(battleshipCountOpp + dto.getDamages().getBattleshipHits());
                }
                submarineCountOpp = dto.getDamages().getSubmarine();
                patrolboatCountOpp = dto.getDamages().getPatrolboat();
                destroyerCountOpp = dto.getDamages().getDestroyer();
                carrierCountOpp = dto.getDamages().getCarrier();
                battleshipCountOpp = dto.getDamages().getBattleship();
                hitsDto.getOpponent().add(dto);
            }
        }
        return hitsDto;
    }

    private int getCountHitsDto(GamePlayer gamePlayer, GamePlayer opponentGamePlayer){
        int submarineCount = 0;
        int patrolboatCount = 0;
        int destroyerCount = 0;
        int carrierCount = 0;
        int battleshipCount = 0;

        if (opponentGamePlayer.getSalvoes().size() >= 1) {
            var aux = fromSetToList(opponentGamePlayer.getSalvoes());
            for (Salvo salvo : aux) {
                var dto = makeSelfAndOppDto(salvo, gamePlayer);
                if (salvo.getTurn() != 1) {
                    dto.getDamages().setSubmarine(submarineCount + dto.getDamages().getSubmarineHits());
                    dto.getDamages().setPatrolboat(patrolboatCount + dto.getDamages().getPatrolboatHits());
                    dto.getDamages().setDestroyer(destroyerCount + dto.getDamages().getDestroyerHits());
                    dto.getDamages().setCarrier(carrierCount + dto.getDamages().getCarrierHits());
                    dto.getDamages().setBattleship(battleshipCount + dto.getDamages().getBattleshipHits());
                }
                submarineCount = dto.getDamages().getSubmarine();
                patrolboatCount = dto.getDamages().getPatrolboat();
                destroyerCount = dto.getDamages().getDestroyer();
                carrierCount = dto.getDamages().getCarrier();
                battleshipCount = dto.getDamages().getBattleship();
            }
        }
        return submarineCount + patrolboatCount + destroyerCount + carrierCount + battleshipCount;
    }



    private SelfAndOppDto makeSelfAndOppDto(Salvo salvo, GamePlayer opponentGamePlayer) {
        var selfAndOppDto = new SelfAndOppDto();
        selfAndOppDto.setTurn(salvo.getTurn());
        selfAndOppDto.setHitLocations(makeHits(salvo, opponentGamePlayer.getShips()));
        selfAndOppDto.setDamages(makeDamagesDto(salvo, opponentGamePlayer.getShips()));
        selfAndOppDto.setMissed(countHitsMissed(salvo, makeHits(salvo, opponentGamePlayer.getShips())));
        return selfAndOppDto;
    }

    private DamagesDto makeDamagesDto(Salvo salvo, Set<Ship> ships) {
        var damagesDto = new DamagesDto();
        for (Ship ship : ships) {
            if (ship.getType().equals("carrier")) {
                damagesDto.setCarrierHits(countHits(salvo, ship));
                if (countHits(salvo, ship) != 0) {
                    damagesDto.setCarrier(damagesDto.getCarrier() + countHits(salvo, ship));
                }
            }
            if (ship.getType().equals("battleship")) {
                damagesDto.setBattleshipHits(countHits(salvo, ship));
                if (countHits(salvo, ship) != 0) {
                    damagesDto.setBattleship(damagesDto.getBattleship() + countHits(salvo, ship));
                }
            }
            if (ship.getType().equals("submarine")) {
                damagesDto.setSubmarineHits(countHits(salvo, ship));
                if (countHits(salvo, ship) != 0) {
                    damagesDto.setSubmarine(damagesDto.getSubmarine() + countHits(salvo, ship));
                }
            }
            if (ship.getType().equals("destroyer")) {
                damagesDto.setDestroyerHits(countHits(salvo, ship));
                if (countHits(salvo, ship) != 0) {
                    damagesDto.setDestroyer(damagesDto.getDestroyer() + countHits(salvo, ship));
                }
            }
            if (ship.getType().equals("patrolboat")) {
                damagesDto.setPatrolboatHits(countHits(salvo, ship));
                if (countHits(salvo, ship) != 0) {
                    damagesDto.setPatrolboat(damagesDto.getPatrolboat() + countHits(salvo, ship));
                }
            }
        }
        return damagesDto;
    }

    private List<String> makeHits(Salvo salvo, Set<Ship> ships) {
        List<String> hits = new ArrayList<>();
        ships.forEach(ship -> hits.addAll(makeList(salvo.getSalvoLocations(),
                ship.getShipLocations())));
        return hits;
    }

    private List<String> makeList(List<String> shipsLocations, List<String> salvoesLocations) {
        return shipsLocations.stream().flatMap(shipLocation ->
                salvoesLocations.stream().filter(shipLocation::equals)).collect(Collectors.toList());
    }

    private int countHitsMissed(Salvo salvo, List<String> locations) {
        return salvo.getSalvoLocations().size() - locations.size();
    }

    private int countHits(Salvo salvo, Ship ship) {
        int result = 0;
        for (String salvoLocation : salvo.getSalvoLocations()) {
            for (String location : ship.getShipLocations()) {
                if (salvoLocation.equals(location)) {
                    result++;
                }
            }
        }
        return result;
    }

}
