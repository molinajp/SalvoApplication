package com.codeoftheweb.salvo.controllers;

import com.codeoftheweb.salvo.models.Salvo;
import com.codeoftheweb.salvo.services.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.codeoftheweb.salvo.util.Util.*;
import static com.codeoftheweb.salvo.util.Util.areSalvoLocationsRepeated;

@RestController
@RequestMapping("/api")
public class SalvosController {

    @Autowired
    private RepoService repoService;

    @PostMapping("/games/players/{gamePlayerId}/salvoes")
    public ResponseEntity<?> createSalvoesLocations(@PathVariable Long gamePlayerId,
                                                    @RequestBody Salvo salvo, Authentication authentication) {

        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "No hay un usuario logueado"), HttpStatus.UNAUTHORIZED);
        } else {
            var gp = repoService.getGamePlayer(gamePlayerId);
            if (gp.isEmpty()) {
                return new ResponseEntity<>(makeMap("error", "No existe un gamePlayer con esa ID"), HttpStatus.UNAUTHORIZED);
            } else if (!gp.get().getPlayer().equals(repoService.getPlayer(authentication.getName()).get())) {
                return new ResponseEntity<>(makeMap("error", "No está autorizado"), HttpStatus.UNAUTHORIZED);
            } else if (gp.get().getShips().size() == 0) {
                return new ResponseEntity<>(makeMap("error", "Primero debe colocar los barcos"), HttpStatus.FORBIDDEN);
            } else if (salvo.getSalvoLocations() != null &&
                    (salvo.getSalvoLocations().size() > 5 || salvo.getSalvoLocations().size() < 1)) {
                return new ResponseEntity<>(makeMap("error", "Deben haber como maximo 5 disparos y como minimo 1"),
                        HttpStatus.FORBIDDEN);
            } else if (!areSalvoLocationsOk(salvo.getSalvoLocations())) {
                return new ResponseEntity<>(makeMap("error", "Ha ingresado ubicaciones no válidas, repetidas  o " +
                        " inexistentes para los disparos. Debe introducir una letra de la A a la J (mayusculas)" +
                        " y un número del 1 al 10, sin espacios"), HttpStatus.FORBIDDEN);
            } else if (areSalvoLocationsRepeated(salvo.getSalvoLocations(),
                    gp.get().getSalvoes().stream().map(Salvo::getSalvoLocations).collect(Collectors.toList())) != null) {
                return new ResponseEntity<>(makeMap("error", "Hay uno o más disparos que ya han sido colocado antes"),
                        HttpStatus.FORBIDDEN);
            } else if(gp.get().getScore().isPresent() && gp.get().getScore().get().getFinishDate() != null){
                return new ResponseEntity<>(makeMap("error", "El juego ya ha terminado"), HttpStatus.FORBIDDEN);
            }
            var opponentGP = getOpponent(gp.get());
            if (opponentGP.isPresent()) {
                if (opponentGP.get().getShips().size() == 0) {
                    return new ResponseEntity<>(makeMap("error", "Debe esperar a que su oponente coloque sus barcos"),
                            HttpStatus.FORBIDDEN);
                } else if (gp.get().getSalvoes().size() > opponentGP.get().getSalvoes().size()) {
                    return new ResponseEntity<>(makeMap("error", "Debe esperar al turno de su oponente"),
                            HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>(makeMap("error", "Todavia no hay un oponente"), HttpStatus.FORBIDDEN);
            }
            salvo.setTurn(gp.get().getSalvoes().size() + 1);
            salvo.setGamePlayer(gp.get());
            repoService.saveSalvo(salvo);
            return new ResponseEntity<>(makeMap("OK", "Los disparos han sido colocados correctamente"),
                    HttpStatus.CREATED);
        }
    }
}
