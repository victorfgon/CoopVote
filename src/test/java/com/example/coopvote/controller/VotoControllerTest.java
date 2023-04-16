package com.example.coopvote.controller;

import com.example.coopvote.model.Voto;
import com.example.coopvote.service.VotoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VotoControllerTest {

    @Mock
    private VotoService votoService;

    @InjectMocks
    private VotoController votoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVotar() {
        Voto voto = new Voto();
        voto.setId("1");
        when(votoService.votar(any(Voto.class))).thenReturn(voto);
        ResponseEntity<Voto> response = votoController.votar(voto);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(voto, response.getBody());
    }

    @Test
    public void testBuscarTodos() {
        List<Voto> votos = new ArrayList<>();
        Voto voto1 = new Voto();
        voto1.setId("1");
        Voto voto2 = new Voto();
        voto2.setId("2");
        votos.add(voto1);
        votos.add(voto2);
        when(votoService.buscarTodos()).thenReturn(votos);
        ResponseEntity<List<Voto>> response = votoController.buscarTodos();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(votos, response.getBody());
    }

    @Test
    public void testApagarTodos() {
        ResponseEntity<Void> response = votoController.apagarTodos();
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
