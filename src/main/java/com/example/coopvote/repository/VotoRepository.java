package com.example.coopvote.repository;

import com.example.coopvote.model.Voto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends MongoRepository<Voto, String> {
    Voto findByIdPautaAndCpf(String idPauta, String cpf);
}
