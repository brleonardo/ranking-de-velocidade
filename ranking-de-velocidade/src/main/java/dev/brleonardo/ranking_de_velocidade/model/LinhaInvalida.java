package dev.brleonardo.ranking_de_velocidade.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LinhaInvalida {
    private int numero;
    private String conteudo;
    private String motivo;
}
