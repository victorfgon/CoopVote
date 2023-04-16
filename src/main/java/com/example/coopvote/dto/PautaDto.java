package com.example.coopvote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@ApiModel(value = "PautaDto", description = "Objeto de transferência de dados que representa uma pauta.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PautaDto {

    @ApiModelProperty(value = "Identificador da pauta")
    private String id;

    @NotBlank(message = "O título da pauta é obrigatório")
    @Size(max = 255, message = "O título da pauta deve ter no máximo {max} caracteres")
    @ApiModelProperty(value = "Título da pauta")
    private String titulo;

    @NotBlank(message = "A descrição da pauta é obrigatória")
    @Size(max = 1024, message = "A descrição da pauta deve ter no máximo {max} caracteres")
    @ApiModelProperty(value = "Descrição da pauta")
    private String descricao;

    @PositiveOrZero(message = "A duração da pauta não pode ser negativa")
    @ApiModelProperty(value = "Duração em minutos da pauta")
    private Integer duracaoEmMinutos = 1;

    public PautaDto(String titulo, String descricao, Integer duracaoEmMinutos) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.duracaoEmMinutos = duracaoEmMinutos;
    }
}
