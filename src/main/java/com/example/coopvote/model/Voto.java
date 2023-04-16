package com.example.coopvote.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "votos")
@ApiModel(description = "Classe que representa um voto")
public class Voto {

    @Id
    @ApiModelProperty(value = "Identificador único do voto", example = "609027d548c1b43691d9a9ac")
    private String id;

    @NotBlank(message = "O ID da pauta não pode estar em branco")
    @ApiModelProperty(value = "Identificador único da pauta associada ao voto", example = "609027d548c1b43691d9a9ac")
    private String idPauta;

    @NotBlank(message = "O CPF do votante não pode estar em branco")
    @ApiModelProperty(value = "CPF do votante", example = "12345678901")
    private String cpf;

    @NotNull(message = "O voto não pode ser nulo")
    @ApiModelProperty(value = "Valor booleano que representa o voto (true = Sim, false = Não)", example = "true")
    private Boolean voto;
}
