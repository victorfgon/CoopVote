package com.example.coopvote.model;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PautaTest {

    @Test
    void testSessaoEncerrada() {
        Pauta pauta = new Pauta();
        pauta.setInicioSessao(LocalDateTime.now().minusMinutes(2));
        pauta.setFimSessao(LocalDateTime.now().minusMinutes(1));

        assertTrue(pauta.sessaoEncerrada());
    }

    @Test
    void testSessaoNaoEncerrada() {
        Pauta pauta = new Pauta();
        pauta.setInicioSessao(LocalDateTime.now().minusMinutes(1));
        pauta.setFimSessao(LocalDateTime.now().plusMinutes(1));

        assertFalse(pauta.sessaoEncerrada());
    }

    @Test
    void testIniciarSessao() {
        Pauta pauta = new Pauta();
        pauta.iniciarSessao();

        assertNotNull(pauta.getInicioSessao());
        assertNotNull(pauta.getFimSessao());
        assertTrue(pauta.getFimSessao().isEqual(pauta.getInicioSessao().plusMinutes(1)));
    }

    @Test
    void testIniciarSessaoWithDuration() {
        Pauta pauta = new Pauta();
        pauta.iniciarSessao(2);

        assertNotNull(pauta.getInicioSessao());
        assertNotNull(pauta.getFimSessao());
        assertTrue(pauta.getFimSessao().isEqual(pauta.getInicioSessao().plusMinutes(2)));
    }

    @Test
    void testAddRemoveVoto() {
        Pauta pauta = new Pauta();
        Voto voto = new Voto();
        voto.setCpf("12345678900");
        voto.setVotoEscolhido(true);

        pauta.addVoto(voto);
        assertEquals(1, pauta.getVotos().size());

        pauta.removeVoto(voto);
        assertEquals(0, pauta.getVotos().size());
    }

}