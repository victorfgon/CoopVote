package com.example.coopvote.service;

import com.example.coopvote.dto.VotoDto;
import com.example.coopvote.exception.SessaoVotacaoEncerradaException;
import com.example.coopvote.exception.VotoDuplicadoException;
import com.example.coopvote.model.Pauta;
import com.example.coopvote.model.Voto;
import com.example.coopvote.repository.PautaRepository;
import com.example.coopvote.repository.VotoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private PautaService pautaService;

    private final Logger logger = LoggerFactory.getLogger(VotoService.class);

    @ApiOperation(value = "Registrar um voto em uma pauta", response = Voto.class)
    public Voto votar(@ApiParam(value = "Objeto do tipo Voto a ser registrado", required = true) VotoDto votoDto)  {
        Voto voto = new Voto(votoDto);
        Pauta pauta = pautaService.buscarPorId(voto.getIdPauta());
        if (pauta.sessaoEncerrada()) {
            logger.error("Não é possível registrar voto para a pauta {}, pois a sessão de votação está encerrada.", pauta.getId());
            throw new SessaoVotacaoEncerradaException();
        }
        Voto votoExistente = votoRepository.findByIdPautaAndCpf(voto.getIdPauta(), voto.getCpf());
        if (votoExistente != null) {
            logger.error("Não é possível registrar voto para a pauta {} com CPF {}, pois este já registrou um voto.", voto.getIdPauta(), voto.getCpf());
            throw new VotoDuplicadoException();
        }
        voto.setIdPauta(voto.getIdPauta());
        votoRepository.save(voto);
        pauta.addVoto(voto);
        pautaRepository.save(pauta);
        logger.info("Voto registrado com sucesso para a pauta {} com CPF {}.", voto.getIdPauta(), voto.getCpf());
        return voto;
    }

    @ApiOperation(value = "Buscar todos os votos registrados", response = List.class)
    public List<Voto> buscarTodos() {
        return votoRepository.findAll();
    }

    @ApiOperation(value = "Apagar todos os votos registrados")
    public void apagarTodos() {
        votoRepository.deleteAll();
        logger.info("Todos os votos foram apagados.");
    }
}
