package com.codeoftheweb.salvo.controllers;

import com.codeoftheweb.salvo.models.Game;
import com.codeoftheweb.salvo.models.GamePlayer;
import com.codeoftheweb.salvo.services.GameService;
import com.codeoftheweb.salvo.services.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.codeoftheweb.salvo.util.Util.isGuest;
import static com.codeoftheweb.salvo.util.Util.makeMap;

@RestController
@RequestMapping("/api")
public class GamesController {

    @Autowired
    private GameService gameService;

    @Autowired
    private RepoService repoService;

    @GetMapping("/games")
    public Map<String, Object> getAll(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();
        if (!isGuest(authentication)) {
            var player = repoService.getPlayer(authentication.getName()).get();
            dto.put("player", gameService.makePlayerDto(player));
        } else {
            dto.put("player", "Guest");
        }
        dto.put("games", repoService.getAllGames().stream().map(gameService::makeGameDto).collect(Collectors.toList()));
        return dto;
    }

    @PostMapping("/games")
    public ResponseEntity<?> createNewGame(Authentication authentication) {
        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "Debe estar logueado antes de crear un juego")
                    , HttpStatus.UNAUTHORIZED);
        } else {
            var player = repoService.getPlayer(authentication.getName());
            var game = new Game(Date.from(Instant.now()));
            repoService.saveGame(game);
            var gamePlayer = new GamePlayer(game, player.get(), Date.from(Instant.now()));
            repoService.saveGamePlayer(gamePlayer);
            return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
        }
    }
}
