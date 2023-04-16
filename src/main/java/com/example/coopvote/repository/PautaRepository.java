package com.example.coopvote.repository;

import com.example.coopvote.model.Pauta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PautaRepository extends MongoRepository<Pauta, String> {
}
