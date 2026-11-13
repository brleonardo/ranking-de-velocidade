package dev.brleonardo.ranking_de_velocidade.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.brleonardo.ranking_de_velocidade.model.Analises;
import dev.brleonardo.ranking_de_velocidade.model.Episodio;
import dev.brleonardo.ranking_de_velocidade.model.Linha;
import dev.brleonardo.ranking_de_velocidade.model.LinhaInvalida;
import dev.brleonardo.ranking_de_velocidade.model.Resumo;
import dev.brleonardo.ranking_de_velocidade.model.VeiculoRank;

@Service
public class AnalisesService {

    public Analises analisarArquivo(InputStream linhas) {

        var linhasValidas = new ArrayList<Linha>();
        var linhasInvalidas = new ArrayList<LinhaInvalida>();
        String linha = "";
        int linhaAtual = 0;

        BufferedReader reader = new BufferedReader(new InputStreamReader(linhas, StandardCharsets.UTF_8));

        try {
            String cabecalho = reader.readLine(); // removendo o cabecalho

            while ((linha = reader.readLine()) != null) {
                linhaAtual += 1;
                String[] cols = linha.split(";");

                try {
                    String placa = cols[0];
                    LocalDateTime dataHora = LocalDateTime.parse(cols[1]);
                    Double velKmh = Double.parseDouble(cols[2]);
                    Double limKmh = Double.parseDouble(cols[3]);

                    linhasValidas.add(new Linha(linhaAtual, placa, dataHora, velKmh, limKmh));
                } catch (Exception e) {
                    // TODO: adicionar motivo
                    linhasInvalidas.add(new LinhaInvalida(linhaAtual, linha, "todo"));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler arquivo");
        }

        List<Episodio> episodios = null;
        try {
            episodios = criarEpisodios(linhasValidas);
        } catch (Exception e) {
            System.err.println(e);
        }

        var resumo = new Resumo(linhaAtual, linhasValidas.size(), episodios.size());

        episodios.sort(
                Comparator.comparingDouble(Episodio::getGravidade)
                        .reversed()
                        .thenComparing(Episodio::getPlaca));

        List<VeiculoRank> ranking = criarRank(episodios);

        return new Analises(episodios, ranking, linhasInvalidas, resumo);
    }

    private List<VeiculoRank> criarRank(List<Episodio> episodios) {
        Map<String, Integer> somaPorPlaca = episodios.stream()
                .collect(Collectors.groupingBy(
                        Episodio::getPlaca,
                        Collectors.summingInt(Episodio::getGravidade)));

        List<Map.Entry<String, Integer>> rankingFinal = somaPorPlaca.entrySet().stream()
                .sorted(
                        Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry.comparingByKey()))
                .toList();

        var resultado = new ArrayList<VeiculoRank>();

        for (Map.Entry<String, Integer> item : rankingFinal) {
            resultado.add(new VeiculoRank(item.getValue(), item.getKey()));
        }

        return resultado;
    }

    private List<Episodio> criarEpisodios(List<Linha> linhas) {
        Map<String, List<List<Linha>>> linhasAgrupadas = linhas.stream()
                .collect(Collectors.groupingBy(Linha::getPlaca))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> agruparPorIntervalo(
                                entry.getValue().stream()
                                        .sorted(Comparator.comparing(Linha::getDataHora))
                                        .collect(Collectors.toList()))));

        var episodios = new ArrayList<Episodio>();

        for (Map.Entry<String, List<List<Linha>>> entry : linhasAgrupadas.entrySet()) {

            var grupos = entry.getValue();

            for (List<Linha> grupo : grupos) {
                Episodio episodio = null;
                Linha anterior = null;

                for (Linha linha : grupo) {

                    boolean excesso = linha.getVelocidadeKmh() > linha.getLimiteKmh() + 5.0;

                    if (!excesso) {
                        if (episodio != null) {
                            episodios.add(episodio);
                            episodio = null;
                        }

                        anterior = null;
                        continue;
                    }

                    if (episodio == null) {

                        episodio = atualizarEpisodio(linha, grupo.size());

                    } else {

                        Duration diferenca = Duration.between(
                                anterior.getDataHora(),
                                linha.getDataHora());

                        if (diferenca.compareTo(Duration.ofMinutes(5)) <= 0) {

                            episodio.setFim(linha.getDataHora());

                            double duracao = Duration.between(
                                    episodio.getInicio(),
                                    linha.getDataHora()).toMillis() / 60000.0;

                            episodio.setDuracao(duracao);

                            if (linha.getVelocidadeKmh() > episodio.getVelocidadeMaxima()) {
                                episodio.setVelocidadeMaxima(linha.getVelocidadeKmh());
                            }

                        } else {
                            episodios.add(episodio);
                            episodio = atualizarEpisodio(linha, grupo.size());
                        }
                    }

                    if (episodio.getExcessoMaximo() <= 20)
                        episodio.setGravidade(1);
                    else if (episodio.getExcessoMaximo() > 20 && episodio.getExcessoMaximo() <= 50)
                        episodio.setGravidade(3);
                    else if (episodio.getExcessoMaximo() > 50)
                        episodio.setGravidade(5);

                    anterior = linha;
                }

                if (episodio != null) {
                    episodios.add(episodio);
                }
            }
        }

        return episodios;
    }

    private Episodio atualizarEpisodio(Linha linha, int qtdLinhas) {
        Episodio episodio = new Episodio();
        episodio.setPlaca(linha.getPlaca());
        episodio.setExcessoMaximo(
                (linha.getVelocidadeKmh() - linha.getLimiteKmh()) / linha.getLimiteKmh() * 100);
        episodio.setInicio(linha.getDataHora());
        episodio.setFim(linha.getDataHora());
        episodio.setDuracao(0.0);
        episodio.setLeituras(qtdLinhas);
        episodio.setVelocidadeMaxima(linha.getVelocidadeKmh());
        return episodio;
    }

    private List<List<Linha>> agruparPorIntervalo(List<Linha> linhas) {
        List<List<Linha>> grupos = new ArrayList<>();

        List<Linha> grupoAtual = new ArrayList<>();

        linhas.sort(Comparator.comparing(Linha::getDataHora));

        for (Linha linha : linhas) {

            if (grupoAtual.isEmpty()) {
                grupoAtual.add(linha);
                continue;
            }

            Linha anterior = grupoAtual.get(grupoAtual.size() - 1);

            Duration diferenca = Duration.between(
                    anterior.getDataHora(),
                    linha.getDataHora());

            if (diferenca.compareTo(Duration.ofMinutes(5)) <= 0) {
                grupoAtual.add(linha);
            } else {
                grupos.add(grupoAtual);
                grupoAtual = new ArrayList<>();
                grupoAtual.add(linha);
            }
        }

        if (!grupoAtual.isEmpty()) {
            grupos.add(grupoAtual);
        }

        return grupos;
    }
}
