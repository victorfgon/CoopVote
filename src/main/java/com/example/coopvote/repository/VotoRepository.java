package com.example.coopvote.repository;

import com.example.coopvote.model.Voto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VotoRepository extends MongoRepository<Voto, String> {
    Voto findByIdPautaAndCpf(String idPauta, String cpf);
}
