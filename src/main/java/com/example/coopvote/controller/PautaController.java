package com.example.coopvote.controller;

import com.example.coopvote.dto.PautaDto;
import com.example.coopvote.model.Pauta;
import com.example.coopvote.service.PautaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pautas")
@Api(value = "Pauta", tags = { "Pauta" })
public class PautaController {

    private final Logger logger = LoggerFactory.getLogger(PautaController.class);

    @Autowired
    private PautaService pautaService;

    @PostMapping
    @ApiOperation(value = "Cria uma nova pauta", response = Pauta.class)
    public ResponseEntity<Pauta> criarPauta(@RequestBody @Valid PautaDto pauta) {
        logger.info("Requisição recebida para criar uma nova pauta");
        Pauta novaPauta = pautaService.criarPauta(pauta);
        logger.info("Pauta criada com sucesso com id {}", novaPauta.getId());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(novaPauta.getId()).toUri();
        return ResponseEntity.created(uri).body(novaPauta);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca uma pauta por id", response = Pauta.class)
    public ResponseEntity<Pauta> buscarPorId(@PathVariable String id) {
        logger.info("Requisição recebida para buscar pauta com id {}", id);
        Pauta pauta = pautaService.buscarPorId(id);
        if (pauta == null) {
            logger.info("Pauta com id {} não encontrada", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Pauta com id {} encontrada com sucesso", id);
        return ResponseEntity.ok(pauta);
    }

    @GetMapping
    @ApiOperation(value = "Busca todas as pautas", response = List.class)
    public ResponseEntity<List<Pauta>> buscarTodas() {
        logger.info("Requisição recebida para buscar todas as pautas");
        List<Pauta> pautas = pautaService.buscarTodas();
        logger.info("{} pautas encontradas", pautas.size());
        return ResponseEntity.ok(pautas);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove uma pauta por id")
    public ResponseEntity<Void> removerPauta(@PathVariable String id) {
        logger.info("Requisição recebida para remover pauta com id {}", id);
        pautaService.removerPauta(id);
        logger.info("Pauta com id {} removida com sucesso", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/resultado")
    @ApiOperation(value = "Calcula o resultado de votação de uma pauta por id", response = String.class)
    public ResponseEntity<String> calcularResultadoVotacao(@PathVariable String id) {
        logger.info("Calculando resultado de votação para a pauta com id {}", id);
        Pauta pauta = pautaService.buscarPorId(id);
        if (pauta == null) {
            logger.warn("Não foi possível encontrar a pauta com id {}", id);
            return ResponseEntity.notFound().build();
        }
        if (pauta.getResultadoVotacao() != null){
            return ResponseEntity.ok(pauta.getResultadoVotacao());
        }
        if (pauta.sessaoEncerrada()) {
            logger.info("A sessão de votação para a pauta com id {} está encerrada. Calculando resultado...", id);
            String resultado = pautaService.calcularResultadoVotacao(id);
            logger.info("Resultado de votação para a pauta com id {}: {}", id, resultado);
            return ResponseEntity.ok(resultado);
        } else {
            logger.warn("A sessão de votação para a pauta com id {} ainda está em andamento. Não é possível calcular o resultado.", id);
            return ResponseEntity.badRequest().body("A sessão de votação ainda está em andamento.");
        }
    }
}