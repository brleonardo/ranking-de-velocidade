package dev.brleonardo.ranking_de_velocidade.dto;

import java.util.List;

import dev.brleonardo.ranking_de_velocidade.model.Episodio;
import dev.brleonardo.ranking_de_velocidade.model.LinhaInvalida;
import dev.brleonardo.ranking_de_velocidade.model.Resumo;
import dev.brleonardo.ranking_de_velocidade.model.VeiculoRank;

public record AnalisesResponseDTO(List<Episodio> episodios, List<VeiculoRank> ranking,
        List<LinhaInvalida> linhasInvalidas,
        Resumo resumo) {
}
