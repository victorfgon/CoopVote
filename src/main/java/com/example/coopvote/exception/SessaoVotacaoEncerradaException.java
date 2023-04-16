package com.example.coopvote.exception;

public class SessaoVotacaoEncerradaException extends RuntimeException {
    public SessaoVotacaoEncerradaException() {
        super("Não é possível votar pois a sessão de votação já foi encerrada");
    }
}
