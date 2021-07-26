package com.codeoftheweb.salvo.util;

import com.codeoftheweb.salvo.models.GamePlayer;
import com.codeoftheweb.salvo.models.Salvo;
import com.codeoftheweb.salvo.models.Ship;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.*;
import java.util.stream.Collectors;

public class Util {

    public static Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(key, value);
        return result;
    }

    public static boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    public static boolean areShipLocationsOkay(List<String> locations) {
        String comparador = null;
        boolean result = false;
        for (String l : locations) {
            if (l.length() == 3 || l.length() == 2) {
                if (l.charAt(0) < 'A' || l.charAt(0) > 'J') {
                    return false;
                } else if (l.length() == 2 && (l.charAt(1) < '0' && l.charAt(1) > '9')) {
                    return false;
                } else if (l.length() == 3 && !(l.substring(1).equals("10"))) {
                    return false;
                } else if (comparador != null && comparador.length() == 2 && l.length() != 3) {
                    boolean cambioPrimerChar = (int) comparador.charAt(0) == (int) l.charAt(0) - 1 ||
                            (int) comparador.charAt(0) == (int) l.charAt(0) + 1;
                    boolean cambioSegundoChar = (int) comparador.charAt(1) == (int) l.charAt(1) - 1 ||
                            (int) comparador.charAt(1) == (int) l.charAt(1) + 1;
                    result = (cambioPrimerChar && comparador.charAt(1) == l.charAt(1)) ||
                            (cambioSegundoChar && comparador.charAt(0) == l.charAt(0));
                } else if (comparador != null && comparador.length() == 3 && l.length() != 3) {
                    boolean cambioPrimerChar = (int) comparador.charAt(0) == (int) l.charAt(0) - 1 ||
                            (int) comparador.charAt(0) == (int) l.charAt(0) + 1;
                    result = l.charAt(1) == '9' && !cambioPrimerChar;
                } else if (l.length() == 3 && comparador != null && comparador.length() == 3) {
                    boolean cambioPrimerChar = (int) comparador.charAt(0) == (int) l.charAt(0) - 1 ||
                            (int) comparador.charAt(0) == (int) l.charAt(0) + 1;
                    result = ((comparador.charAt(0) == l.charAt(0)) && (comparador.charAt(1) == '9')) ||
                            (cambioPrimerChar && l.substring(1).equals(comparador.substring(1)));
                } else if (l.length() == 3 && comparador != null){
                    boolean cambioPrimerChar = (int) comparador.charAt(0) == (int) l.charAt(0) - 1 ||
                            (int) comparador.charAt(0) == (int) l.charAt(0) + 1;
                    if(!cambioPrimerChar){
                        result = true;
                    }
                }
                if (comparador != null && !result) {
                    return false;
                }
                comparador = l;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean areShipsLocationsRepeated(List<Ship> ships) {
        Set<String> check = new HashSet<>();
        if (ships.stream().filter(ship -> ship.getShipLocations() != null)
                .collect(Collectors.toSet()).size() == ships.size()) {
            ships.forEach(ship -> check.addAll(ship.getShipLocations()));
        }
        return check.size() != 17;
    }

    public static boolean areTypesOk(String type) {
        switch (type) {
            case "carrier":
            case "patrolboat":
            case "submarine":
            case "destroyer":
            case "battleship":
                return true;
        }
        return false;
    }

    public static String areSalvoLocationsRepeated(List<String> a, List<List<String>> b) {
        String result = null;
        for (String dis : a) {
            for (List<String> list : b) {
                for (String other : list) {
                    if (dis.equals(other)) {
                        result = dis;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static boolean areSalvoLocationsRepeated(List<String> a) {
        Set<String> help = new HashSet<>(a);
        return help.size() != a.size();
    }

    public static boolean areSalvoLocationsOk(List<String> locations) {
        if (locations != null) {
            for (String l : locations) {
                if (l.length() == 3 || l.length() == 2) {
                    if (l.charAt(0) < 'A' || l.charAt(0) > 'J') {
                        return false;
                    } else if (l.length() == 2 && (l.charAt(1) < '0' && l.charAt(1) > '9')) {
                        return false;
                    } else if (l.length() == 3 && !(l.substring(1).equals("10"))) {
                        return false;
                    } else if (areSalvoLocationsRepeated(locations)) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static List<Salvo> fromSetToList(Set<Salvo> salvos) {
        List<Salvo> aux = new ArrayList<>();
        for (int i = 1; i <= salvos.size(); i++) {
            int finalI = i;
            var salvo = salvos.stream().filter(s -> s.getTurn() == finalI).findFirst();
            salvo.ifPresent(aux::add);
        }
        return aux;
    }

    public static Optional<GamePlayer> getOpponent(GamePlayer gamePlayer){
        return gamePlayer.getGame().getGamePlayers().stream().filter
                (gp -> gp.getId() != gamePlayer.getId()).findFirst();
    }
}
