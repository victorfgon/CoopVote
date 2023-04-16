package com.example.coopvote.model;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.coopvote.dto.PautaDto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Document(collection = "pautas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pauta {

    @Id
    @ApiModelProperty(notes = "Identificador único da pauta", example = "609027d548c1b43691d9a9ac")
    private String id;

    @NotBlank
    @ApiModelProperty(notes = "Título da pauta", example = "Reforma do Estatuto")
    private String titulo;

    @NotBlank
    @ApiModelProperty(notes = "Descrição da pauta", example = "Aprovação da reforma do estatuto da empresa")
    private String descricao;

    private List<Voto> votos = new ArrayList<>();

    @PastOrPresent
    @ApiModelProperty(notes = "Data e hora de início da sessão", example = "2022-10-10T10:00:00")
    private LocalDateTime inicioSessao;

    @NotNull
    @Future
    @ApiModelProperty(notes = "Data e hora de término da sessão", example = "2022-10-10T11:00:00")
    private LocalDateTime fimSessao;

    @ApiModelProperty(notes = "Resultado da votação", example = "Pauta rejeitada com 1 votos não e 0 votos sim")
    private String resultadoVotacao;


    public Pauta(PautaDto pautaDto) {
        this.titulo = pautaDto.getTitulo();
        this.descricao = pautaDto.getDescricao();
    }

    public void addVoto(Voto voto) {
        votos.add(voto);
    }

    public void removeVoto(Voto voto) {
        votos.remove(voto);
    }

    public boolean sessaoEncerrada() {
        return LocalDateTime.now().isAfter(fimSessao);
    }

    public void iniciarSessao() {
        this.inicioSessao = LocalDateTime.now();
        this.fimSessao = this.inicioSessao.plusMinutes(1);
    }

    public void iniciarSessao(long duracaoEmMinutos) {
        LocalDateTime now = LocalDateTime.now();
        this.inicioSessao = now;
        this.fimSessao = now.plusMinutes(duracaoEmMinutos);
    }

}