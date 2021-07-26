package com.codeoftheweb.salvo.controllers;

import com.codeoftheweb.salvo.models.Ship;
import com.codeoftheweb.salvo.services.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.codeoftheweb.salvo.util.Util.*;

@RestController
@RequestMapping("/api")
public class ShipsController {

    @Autowired
    private RepoService repoService;

    @PostMapping("/games/players/{gamePlayerId}/ships")
    public ResponseEntity<?> createShipsLocations(@PathVariable Long gamePlayerId,
                                                  @RequestBody List<Ship> ships, Authentication authentication) {
        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "No hay un usuario logueado"), HttpStatus.FORBIDDEN);
        } else {
            if (!repoService.existsGamePlayer(gamePlayerId)) {
                return new ResponseEntity<>(makeMap("error", "No existe un gamePlayer con esa ID"),
                        HttpStatus.UNAUTHORIZED);
            } else if (!repoService.getGamePlayer(gamePlayerId).get().getPlayer().equals(
                    repoService.getPlayer(authentication.getName()).get())) {
                return new ResponseEntity<>(makeMap("error", "No está autorizado"), HttpStatus.UNAUTHORIZED);
            } else if (repoService.getGamePlayer(gamePlayerId).get().getShips().size() != 0) {
                return new ResponseEntity<>(makeMap("error", "Los barcos ya han sido colocados"), HttpStatus.FORBIDDEN);
            }
            if (ships.size() == 5) {
                for (Ship ship : ships) {
                    if (!areTypesOk(ship.getType())) {
                        return new ResponseEntity<>(makeMap("error", "Debe colocar uno de los siguientes nombres como" +
                                " tipo de barco: carrier, battleship, destroyer, submarine  o patrolboat"),
                                HttpStatus.FORBIDDEN);
                    }
                    if (ship.getType().equals("carrier") && ship.getShipLocations().size() != 5) {
                        return new ResponseEntity<>(makeMap("error", "El barco  carrier debe tener 5 ubicaciones"),
                                HttpStatus.FORBIDDEN);
                    } else if (ship.getType().equals("battleship") && ship.getShipLocations().size() != 4) {
                        return new ResponseEntity<>(makeMap("error", "El barco battleship debe tener 4 ubicaciones"),
                                HttpStatus.FORBIDDEN);
                    } else if (ship.getType().equals("destroyer") && ship.getShipLocations().size() != 3) {
                        return new ResponseEntity<>(makeMap("error", "El barco destroyer debe tener 3 ubicaciones"),
                                HttpStatus.FORBIDDEN);
                    } else if (ship.getType().equals("submarine") && ship.getShipLocations().size() != 3) {
                        return new ResponseEntity<>(makeMap("error", "El barco  submarine debe tener 3 ubicaciones"),
                                HttpStatus.FORBIDDEN);
                    } else if (ship.getType().equals("patrolboat") && ship.getShipLocations().size() != 2) {
                        return new ResponseEntity<>(makeMap("error", "El barco patrolboat debe tener 2 ubicaciones"),
                                HttpStatus.FORBIDDEN);
                    } else if (!areShipLocationsOkay(ship.getShipLocations())) {
                        return new ResponseEntity<>(makeMap("error", "Ha ingresado ubicaciones no válidas para los barcos." +
                                " Debe introducir una letra de la A a la J (mayusculas) y un número del 1 al 10," +
                                " en orden alfabetico o numerico, sin espacios"), HttpStatus.FORBIDDEN);
                    }
                }

            } else {
                return new ResponseEntity<>(makeMap("error", "Se deben colocar 5 barcos"), HttpStatus.FORBIDDEN);
            } if (areShipsLocationsRepeated(ships)) {
                return new ResponseEntity<>(makeMap("error", "Faltan posiciones de barcos o " +
                        "hay posiciones de barcos repetidas"), HttpStatus.FORBIDDEN);
            }
            var gp = repoService.getGamePlayer(gamePlayerId).get();
            for (Ship ship : ships) {
                ship.setGamePlayer(gp);
                repoService.saveShip(ship);
            }
            return new ResponseEntity<>(makeMap("OK", "Los barcos se han colocado correctamente"),
                    HttpStatus.CREATED);
        }
    }
}
