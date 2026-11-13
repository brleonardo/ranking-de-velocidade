package dev.brleonardo.ranking_de_velocidade.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Episodio {
    private String placa;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Double duracao;
    private Double velocidadeMaxima;
    private Double excessoMaximo;
    private int leituras;
    private int gravidade;
}
