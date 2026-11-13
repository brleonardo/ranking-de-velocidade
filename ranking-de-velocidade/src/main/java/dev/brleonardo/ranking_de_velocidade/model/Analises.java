package dev.brleonardo.ranking_de_velocidade.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Analises {
    private List<Episodio> episodios;
    private List<VeiculoRank> ranking;
    private List<LinhaInvalida> linhasInvalidas;
    private Resumo resumo;
}
