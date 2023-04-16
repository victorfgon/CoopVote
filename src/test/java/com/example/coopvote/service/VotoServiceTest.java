package com.example.coopvote.service;

import com.example.coopvote.dto.VotoDto;
import com.example.coopvote.exception.SessaoVotacaoEncerradaException;
import com.example.coopvote.exception.VotoDuplicadoException;
import com.example.coopvote.model.Pauta;
import com.example.coopvote.model.Voto;
import com.example.coopvote.repository.PautaRepository;
import com.example.coopvote.repository.VotoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private VotoService votoService;

    private Voto voto;
    private Pauta pauta;
    private VotoDto votoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pauta = new Pauta();
        pauta.setId("1");
        pauta.setDescricao("Teste");
        pauta.setInicioSessao(LocalDateTime.now().minusMinutes(30));
        pauta.setFimSessao(LocalDateTime.now().plusMinutes(30));
        pauta.setId("1");
        pauta.setTitulo("Teste");
        voto = new Voto();
        voto.setIdPauta(pauta.getId());
        voto.setCpf("12345678910");
        voto.setVotoEscolhido(true);
        votoDto = new VotoDto();
        votoDto.setIdPauta(pauta.getId());
        votoDto.setCpf("12345678910");
        votoDto.setVotoEscolhido(true);
    }

    @Test
    void testVotar() {
        when(pautaService.buscarPorId(voto.getIdPauta())).thenReturn(pauta);
        when(votoRepository.findByIdPautaAndCpf(voto.getIdPauta(), voto.getCpf())).thenReturn(null);
        when(votoRepository.save(voto)).thenReturn(voto);

        votoService.votar(votoDto);

        verify(votoRepository, times(1)).save(voto);
        verify(pautaRepository, times(1)).save(pauta);
    }

    @Test
    void testVotarSessaoEncerrada() {
        pauta.setInicioSessao(LocalDateTime.now().minusMinutes(30));
        pauta.setFimSessao(LocalDateTime.now().minusMinutes(30));

        when(pautaService.buscarPorId(voto.getIdPauta())).thenReturn(pauta);

        Assertions.assertThrows(SessaoVotacaoEncerradaException.class, () -> votoService.votar(votoDto));
        verify(votoRepository, never()).save(any(Voto.class));
        verify(pautaRepository, never()).save(any(Pauta.class));
    }

    @Test
    void testVotarDuplicado() {
        when(pautaService.buscarPorId(voto.getIdPauta())).thenReturn(pauta);
        when(votoRepository.findByIdPautaAndCpf(voto.getIdPauta(), voto.getCpf())).thenReturn(voto);

        Assertions.assertThrows(VotoDuplicadoException.class, () -> votoService.votar(votoDto));

        verify(votoRepository, never()).save(any(Voto.class));
        verify(pautaRepository, never()).save(any(Pauta.class));
    }

    @Test
    void testBuscarTodos() {
        votoService.buscarTodos();

        verify(votoRepository, times(1)).findAll();
    }

    @Test
    void testApagarTodos() {
        votoService.apagarTodos();

        verify(votoRepository, times(1)).deleteAll();
    }

}
