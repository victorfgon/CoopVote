package com.example.coopvote.exception;

public class PautaNotFoundException extends RuntimeException {
    public PautaNotFoundException(String id) {
        super("Pauta não encontrada com id " + id);
    }
}