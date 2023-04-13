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

        // Getters e Setters omitidos

        public void addVoto(Voto voto) {
            votos.add(voto);
            voto.setPauta(this);
        }

        public void removeVoto(Voto voto) {
            votos.remove(voto);
            voto.setPauta(null);
        }

        // Métodos para controle do tempo de sessão de votação
        public void iniciarSessao() {
            this.inicioSessao = LocalDateTime.now();
            this.fimSessao = this.inicioSessao.plusMinutes(1); // Tempo padrão de 1 minuto para votação
        }

        public void iniciarSessao(Integer duracaoEmMinutos) {
            this.inicioSessao = LocalDateTime.now();
            this.fimSessao = this.inicioSessao.plusMinutes(duracaoEmMinutos);
        }

        public boolean sessaoEncerrada() {
            return LocalDateTime.now().isAfter(fimSessao);
        }
}
