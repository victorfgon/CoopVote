package com.example.coopvote.controller;

import com.example.coopvote.exception.PautaNotFoundException;
import com.example.coopvote.exception.SessaoVotacaoEncerradaException;
import com.example.coopvote.exception.VotoDuplicadoException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class TratamentoDeExcecaoController {

    @ExceptionHandler(SessaoVotacaoEncerradaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String sessaoVotacaoEncerradaExceptionHandler(SessaoVotacaoEncerradaException e) {
        return e.getMessage();
    }

    @ExceptionHandler(VotoDuplicadoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String votoDuplicadoExceptionHandler(VotoDuplicadoException e) {
        return e.getMessage();
    }

    @ExceptionHandler(PautaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String pautaNotFoundExceptionHandler(PautaNotFoundException e) {
        return e.getMessage();
    }
}
