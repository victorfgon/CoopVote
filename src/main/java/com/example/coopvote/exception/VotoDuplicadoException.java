package com.example.coopvote.exception;

public class VotoDuplicadoException extends RuntimeException {
    public VotoDuplicadoException() {
        super("Voto já registrado para esse associado nessa pauta");
    }
}
