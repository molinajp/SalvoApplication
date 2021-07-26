package com.codeoftheweb.salvo.controllers;

import com.codeoftheweb.salvo.models.GamePlayer;
import com.codeoftheweb.salvo.models.Player;
import com.codeoftheweb.salvo.services.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import static com.codeoftheweb.salvo.util.Util.isGuest;
import static com.codeoftheweb.salvo.util.Util.makeMap;

@RestController
@RequestMapping("/api")
public class PlayersController {

    @Autowired
    RepoService repoService;

    @PostMapping("/players")
    public ResponseEntity<?> createPlayer(@RequestParam String email, @RequestParam String password) {
        if (email.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "Debe ingresar un email"), HttpStatus.FORBIDDEN);
        }

        if (password.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "Debe ingresar una contraseña"), HttpStatus.FORBIDDEN);
        }

        var player = repoService.getPlayer(email);
        if (player.isPresent()) {
            return new ResponseEntity<>(makeMap("error", "Este mail ya esta en uso"), HttpStatus.FORBIDDEN);
        }

        Player newPlayer = new Player(email, repoService.encode(password));
        repoService.savePlayer(newPlayer);
        return new ResponseEntity<>(makeMap("username", newPlayer.getUserName()), HttpStatus.CREATED);
    }

    @PostMapping("game/{gameId}/players")
    public ResponseEntity<?> joinGame(@PathVariable Long gameId, Authentication authentication) {
        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "No hay un usuario logueado"), HttpStatus.UNAUTHORIZED);
        } else {
            if (!repoService.existsGame(gameId)) {
                return new ResponseEntity<>(makeMap("error", "No hay un juego con esa ID"), HttpStatus.FORBIDDEN);
            }
            var game = repoService.getGame(gameId);
            var allGp = game.getGamePlayers().stream().filter
                    (gp -> gp.getGame().equals(game)).collect(Collectors.toList());
            var player = repoService.getPlayer(authentication.getName());
            if (allGp.size() == 2) {
                return new ResponseEntity<>(makeMap("error", "El juego está lleno"), HttpStatus.FORBIDDEN);
            } else {
                var gp = allGp.stream().findFirst();
                if (gp.isPresent() && gp.get().getPlayer().equals(player.get())) {
                    return new ResponseEntity<>(makeMap("error", "Ya estás unido a este juego"), HttpStatus.FORBIDDEN);
                }
            }
            var gamePlayer = new GamePlayer(game, player.get(), Date.from(Instant.now()));
            repoService.saveGamePlayer(gamePlayer);
            return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
        }
    }
}
