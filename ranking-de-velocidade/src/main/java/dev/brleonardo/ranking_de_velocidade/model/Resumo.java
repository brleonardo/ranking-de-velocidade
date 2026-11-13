package dev.brleonardo.ranking_de_velocidade.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Resumo {
    private int linhasLidas;
    private int linhasValidas;
    private int totalEpisodios;
}
