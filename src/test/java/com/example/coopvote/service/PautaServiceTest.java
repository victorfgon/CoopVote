package com.example.coopvote.service;

import com.example.coopvote.dto.PautaDto;
import com.example.coopvote.exception.PautaNotFoundException;
import com.example.coopvote.model.Pauta;
import com.example.coopvote.model.Voto;
import com.example.coopvote.repository.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    private PautaDto pautaDto;
    private Pauta pauta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);;

        pautaDto = new PautaDto();
        pautaDto.setTitulo("Teste");
        pautaDto.setDescricao("Descrição teste");
        pautaDto.setDuracaoEmMinutos(10);

        pauta = new Pauta(pautaDto);
        pauta.setId("1");
    }

    @Test
    void testCriarPauta() {
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        Pauta result = pautaService.criarPauta(pautaDto);

        verify(pautaRepository, times(1)).save(any(Pauta.class));
        assertEquals(pauta, result);
    }

    @Test
    void testBuscarPorId() {
        when(pautaRepository.findById(anyString())).thenReturn(Optional.of(pauta));

        Pauta result = pautaService.buscarPorId("1");

        verify(pautaRepository, times(1)).findById("1");
        assertEquals(pauta, result);
    }

    @Test
    void testBuscarPorIdNotFound() {
        when(pautaRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(PautaNotFoundException.class, () -> pautaService.buscarPorId("1"));
    }

    @Test
    void testBuscarTodas() {
        List<Pauta> pautas = List.of(pauta);
        when(pautaRepository.findAll()).thenReturn(pautas);

        List<Pauta> result = pautaService.buscarTodas();

        verify(pautaRepository, times(1)).findAll();
        assertEquals(pautas, result);
    }

    @Test
    void testRemoverPauta() {
        when(pautaRepository.findById(anyString())).thenReturn(Optional.of(pauta));

        pautaService.removerPauta("1");

        verify(pautaRepository, times(1)).findById("1");
        verify(pautaRepository, times(1)).delete(pauta);
    }

    @Test
    void testRemoverPautaNotFound() {
        when(pautaRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(PautaNotFoundException.class, () -> pautaService.removerPauta("1"));
    }

    @Test
    void testCalcularResultadoVotacao() {
        String id = "1";
        pauta = new Pauta();
        pauta.setId(id);
        pauta.setDescricao("Teste");
        pauta.setInicioSessao(LocalDateTime.now().minusMinutes(30));
        pauta.setFimSessao(LocalDateTime.now().plusMinutes(30));
        pauta.setId("1");
        pauta.setTitulo("Teste");
        pauta.setVotos(Arrays.asList(new Voto("1", id,"333", true),
                new Voto("2", id,"333" ,false),
                new Voto("3", id, "333",true)));

        when(pautaRepository.findById(id)).thenReturn(Optional.of(pauta));
        when(pautaRepository.save(pauta)).thenReturn(pauta);
        String resultado = pautaService.calcularResultadoVotacao(id);

        assertEquals("Pauta aprovada com 2 votos sim e 1 votos não", resultado);
    }

    @Test
    void testCalcularResultadoVotacaoNotFound() {
        String id = "1";

        when(pautaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PautaNotFoundException.class, () -> pautaService.calcularResultadoVotacao(id));
    }

}
