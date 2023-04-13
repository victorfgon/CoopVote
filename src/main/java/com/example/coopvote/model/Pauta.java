package com.example.coopvote.model;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pautas")
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titulo;

    private String descricao;

    @OneToMany(
            mappedBy = "pauta",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Voto> votos = new ArrayList<>();

    private LocalDateTime inicioSessao;

    private LocalDateTime fimSessao;

    public Pauta() {
    }

    public Pauta(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    public LocalDateTime getInicioSessao() {
        return inicioSessao;
    }

    public void setInicioSessao(LocalDateTime inicioSessao) {
        this.inicioSessao = inicioSessao;
    }

    public LocalDateTime getFimSessao() {
        return fimSessao;
    }

    public void setFimSessao(LocalDateTime fimSessao) {
        this.fimSessao = fimSessao;
    }

    public void addVoto(Voto voto) {
        votos.add(voto);
        voto.setPauta(this);
    }

    public void removeVoto(Voto voto) {
        votos.remove(voto);
        voto.setPauta(null);
    }

    public boolean sessaoEncerrada() {
        return LocalDateTime.now().isAfter(fimSessao);
    }

    public boolean sessaoAberta() {
        return inicioSessao != null && !sessaoEncerrada();
    }
}