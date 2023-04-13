package com.example.coopvote.repository;

import com.example.coopvote.model.Voto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VotoRepository extends MongoRepository<Voto, String> {
    boolean existsByAssociadoIdAndPautaId(Long associadoId, Long pautaId);
    List<Voto> findByPautaId(Long pautaId);
}
