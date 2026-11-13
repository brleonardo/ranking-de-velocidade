package dev.brleonardo.ranking_de_velocidade.model;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Linha {
    private int numero;
    private String placa;
    private LocalDateTime dataHora;
    private Double velocidadeKmh;
    private Double limiteKmh;
}
