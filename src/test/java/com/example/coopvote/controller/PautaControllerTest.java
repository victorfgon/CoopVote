package com.example.coopvote.controller;

import com.example.coopvote.dto.PautaDto;
import com.example.coopvote.model.Pauta;
import com.example.coopvote.service.PautaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class PautaControllerTest {

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private PautaController pautaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testCriarPauta() {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setTitulo("Teste");
        Pauta novaPauta = new Pauta();
        novaPauta.setId("1");
        novaPauta.setTitulo(pautaDto.getTitulo());
        when(pautaService.criarPauta(any(PautaDto.class))).thenReturn(novaPauta);
        ResponseEntity<Pauta> responseEntity = pautaController.criarPauta(pautaDto);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(novaPauta, responseEntity.getBody());
    }

    @Test
     void testBuscarPorId() {
        String id = "1";
        Pauta pauta = new Pauta();
        pauta.setId(id);
        when(pautaService.buscarPorId(anyString())).thenReturn(pauta);
        ResponseEntity<Pauta> responseEntity = pautaController.buscarPorId(id);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(pauta, responseEntity.getBody());
    }

    @Test
     void testBuscarTodas() {
        List<Pauta> pautas = new ArrayList<>();
        Pauta pauta1 = new Pauta();
        pauta1.setId("1");
        pauta1.setTitulo("Teste 1");
        Pauta pauta2 = new Pauta();
        pauta2.setId("2");
        pauta2.setTitulo("Teste 2");
        pautas.add(pauta1);
        pautas.add(pauta2);
        when(pautaService.buscarTodas()).thenReturn(pautas);
        ResponseEntity<List<Pauta>> responseEntity = pautaController.buscarTodas();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(pautas, responseEntity.getBody());
    }

    @Test
     void testRemoverPauta() {
        String id = "1";
        ResponseEntity<Void> responseEntity = pautaController.removerPauta(id);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
     void testCalcularResultadoVotacaoNotFound() {
        String id = "1";
        when(pautaService.buscarPorId(anyString())).thenReturn(null);
        ResponseEntity<String> responseEntity = pautaController.calcularResultadoVotacao(id);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
     void testMostrarResultadoVotacaoEncerrada() {
        String id = "1";
        Pauta pauta = new Pauta();
        pauta.setId(id);
        pauta.setInicioSessao(LocalDateTime.now().minusMinutes(30));
        pauta.setFimSessao(LocalDateTime.now().minusMinutes(30));
        pauta.setResultadoVotacao("SIM: 2, NÃO: 1");
        when(pautaService.buscarPorId(anyString())).thenReturn(pauta);
        ResponseEntity<String> responseEntity = pautaController.calcularResultadoVotacao(id);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("SIM: 2, NÃO: 1", responseEntity.getBody());
    }

    @Test
    void testCalcularResultadoVotacaoSessaoEmAndamento() {
        String id = "1";
        Pauta pauta = new Pauta();
        pauta.setId(id);
        pauta.setInicioSessao(LocalDateTime.now().minusMinutes(30));
        pauta.setFimSessao(LocalDateTime.now().plusMinutes(30));
        when(pautaService.buscarPorId(anyString())).thenReturn(pauta);
        ResponseEntity<String> responseEntity = pautaController.calcularResultadoVotacao(id);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals("A sessão de votação ainda está em andamento.", responseEntity.getBody());
    }

    @Test
    void testCalcularResultadoVotacao() {
        String id = "1";
        Pauta pauta = new Pauta();
        pauta.setId(id);
        pauta.setInicioSessao(LocalDateTime.now().minusMinutes(30));
        pauta.setFimSessao(LocalDateTime.now().minusMinutes(30));
        when(pautaService.buscarPorId(anyString())).thenReturn(pauta);
        when(pautaService.calcularResultadoVotacao(anyString())).thenReturn("SIM: 2, NÃO: 1");
        ResponseEntity<String> responseEntity = pautaController.calcularResultadoVotacao(id);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("SIM: 2, NÃO: 1", responseEntity.getBody());
    }
}