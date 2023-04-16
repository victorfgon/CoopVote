package com.example.coopvote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Classe que representa um voto")
public class VotoDto {
    
    @ApiModelProperty(value = "Identificador único da pauta associada ao voto", example = "609027d548c1b43691d9a9ac")
    @NotBlank(message = "O ID da pauta não pode estar em branco")
    private String idPauta;

    @ApiModelProperty(value = "CPF do votante", example = "12345678901")
    @NotBlank(message = "O CPF do votante não pode estar em branco")
    private String cpf;

    @ApiModelProperty(value = "Valor booleano que representa o voto (true = Sim, false = Não)", example = "true")
    @NotNull(message = "O voto não pode ser nulo")
    private Boolean votoEscolhido;
}