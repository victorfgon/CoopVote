package com.example.coopvote.controller;

import com.example.coopvote.dto.VotoDto;
import com.example.coopvote.model.Voto;
import com.example.coopvote.service.VotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import javax.validation.Valid;
import java.util.List;

@Api(value = "Voto Controller")
@RestController
@RequestMapping("/api/v1/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    private final Logger logger = LoggerFactory.getLogger(VotoController.class);

    @ApiOperation(value = "Realizar voto em uma pauta")
    @PostMapping
    public ResponseEntity<Voto> votar(@ApiParam(value = "Dados do voto a ser realizado") @RequestBody @Valid VotoDto votoDto) {
        logger.info("Recebendo solicitação para realizar voto em uma pauta.");
        Voto novoVoto = votoService.votar(votoDto);
        logger.info("Voto registrado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(novoVoto);
    }

    @ApiOperation(value = "Buscar todos os votos")
    @GetMapping
    public ResponseEntity<List<Voto>> buscarTodos() {
        logger.info("Recebendo solicitação para buscar todos os votos.");
        List<Voto> votos = votoService.buscarTodos();
        logger.info("Busca de todos os votos realizada com sucesso.");
        return ResponseEntity.ok(votos);
    }

    @ApiOperation(value = "Apagar todos os votos")
    @DeleteMapping
    public ResponseEntity<Void> apagarTodos() {
        logger.info("Recebendo solicitação para apagar todos os votos.");
        votoService.apagarTodos();
        logger.info("Votos apagados com sucesso.");
        return ResponseEntity.noContent().build();
    }
}
