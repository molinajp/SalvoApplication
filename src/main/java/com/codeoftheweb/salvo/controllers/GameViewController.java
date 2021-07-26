package com.codeoftheweb.salvo.controllers;

import com.codeoftheweb.salvo.models.GamePlayer;
import com.codeoftheweb.salvo.services.GameService;
import com.codeoftheweb.salvo.services.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.codeoftheweb.salvo.util.Util.isGuest;
import static com.codeoftheweb.salvo.util.Util.makeMap;

@RestController
@RequestMapping("/api")
public class GameViewController {

    @Autowired
    private GameService gameService;

    @Autowired
    private RepoService repoService;

    @GetMapping("/game_view/{gpId}")
    public ResponseEntity<?> getGameView(@PathVariable Long gpId, Authentication authentication) {
        Optional<GamePlayer> gamePlayer = repoService.getGamePlayer(gpId);
        if (gamePlayer.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "No hay un gamePlayer con esa Id"), HttpStatus.FORBIDDEN);
        }
        if (!isGuest(authentication)) {
            var player = repoService.getPlayer(authentication.getName());
            if (player.get().equals(gamePlayer.get().getPlayer())) {
                return new ResponseEntity<>(gameService.makeGameViewDto(gamePlayer.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(makeMap("error", "No esta autorizado"), HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(makeMap("error", "No hay un usuario logueado"), HttpStatus.UNAUTHORIZED);
        }
    }


}
