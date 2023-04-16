package com.example.coopvote.service;

import com.example.coopvote.dto.PautaDto;
import com.example.coopvote.exception.PautaNotFoundException;
import com.example.coopvote.model.Pauta;
import com.example.coopvote.model.Voto;
import com.example.coopvote.repository.PautaRepository;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaService {
    private final Logger logger = LoggerFactory.getLogger(PautaService.class);

    @Autowired
    private PautaRepository pautaRepository;

    @ApiOperation("Cria uma nova pauta a ser votada no momento")
    public Pauta criarPauta(PautaDto pautaDto) {
        logger.info("Criando nova pauta com titulo {}", pautaDto.getTitulo());
        Pauta pauta = new Pauta(pautaDto);
        if(pautaDto.getDuracaoEmMinutos() > 1){
            pauta.iniciarSessao(pautaDto.getDuracaoEmMinutos());
        }else{
            pauta.iniciarSessao();
        }
        pauta = pautaRepository.save(pauta);
        logger.info("Pauta criada com sucesso. Id = {}", pauta.getId());
        return pauta;
    }

    @ApiOperation("Busca uma pauta por ID")
    public Pauta buscarPorId(String id) {
        logger.info("Buscando pauta com id {}", id);
        return pautaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pauta com ID {} não encontrada", id);
                    return new PautaNotFoundException(id);
                });
    }

    @ApiOperation("Busca todas as pautas")
    public List<Pauta> buscarTodas() {
        logger.info("Buscando todas as pautas");
        return pautaRepository.findAll();
    }

    @ApiOperation("Remove uma pauta por ID")
    public void removerPauta(String id) {
        logger.info("Removendo pauta com id {}", id);
        Pauta pauta = buscarPorId(id);
        pautaRepository.delete(pauta);
        logger.info("Pauta removida com sucesso. Id = {}", id);
    }

    public String calcularResultadoVotacao(String id) {
        logger.info("Calculando resultado de votação para a pauta com ID {}", id);
        Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pauta com ID {} não encontrada", id);
                    return new PautaNotFoundException(id);
                });
        int votosSim = contarVotosSim(pauta);
        int votosNao = contarVotosNao(pauta);

        String resultado;
        if (votosSim > votosNao) {
            resultado = "Pauta aprovada com " + votosSim + " votos sim e " + votosNao + " votos não";
        } else if (votosNao > votosSim) {
            resultado = "Pauta rejeitada com " + votosNao + " votos não e " + votosSim + " votos sim";
        } else {
            resultado = "Empate: " + votosSim + " votos sim e " + votosNao + " votos não";
        }
        pauta.setResultadoVotacao(resultado);
        pautaRepository.save(pauta);
        logger.info("Resultado de votação para a pauta com ID {} calculado com sucesso: {}", id, resultado);
        return resultado;
    }

    private int contarVotosSim(Pauta pauta) {
        return (int) pauta.getVotos().stream()
                .filter(Voto::getVotoEscolhido)
                .count();
    }

    private int contarVotosNao(Pauta pauta) {
        return (int) pauta.getVotos().stream()
                .filter(v -> !v.getVotoEscolhido())
                .count();
    }
}

