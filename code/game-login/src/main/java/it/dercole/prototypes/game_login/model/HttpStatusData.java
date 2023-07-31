package it.dercole.prototypes.game_login.model;

import org.springframework.http.HttpStatus;

public record HttpStatusData(HttpStatus status, int code, String message) {

}
