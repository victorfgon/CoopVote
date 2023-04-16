package com.example.coopvote.repository;

import com.example.coopvote.model.Voto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
class VotoRepositoryTest {

    @Autowired
    private VotoRepository votoRepository;

    @Test
     void testFindByIdPautaAndCpf() {
        Random rand = new Random();
        Voto voto = new Voto();
        voto.setIdPauta("id da pauta");
        voto.setCpf(Integer.toString(rand.nextInt(100000000)));
        voto.setVotoEscolhido(true);
        votoRepository.save(voto);

        Voto votoEncontrado = votoRepository.findByIdPautaAndCpf("id da pauta", voto.getCpf());

        assertNotNull(votoEncontrado);
        assertEquals(voto, votoEncontrado);
    }
}