package dev.brleonardo.ranking_de_velocidade.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import dev.brleonardo.ranking_de_velocidade.dto.AnalisesResponseDTO;
import dev.brleonardo.ranking_de_velocidade.model.Analises;
import dev.brleonardo.ranking_de_velocidade.model.Linha;
import dev.brleonardo.ranking_de_velocidade.service.AnalisesService;

@Controller
public class AnalisesController {

    private AnalisesService analisesService;

    public AnalisesController(AnalisesService analisesService) {
        this.analisesService = analisesService;
    }

    @PostMapping(path = "/api/analises", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnalisesResponseDTO> analisarArquivo(@RequestPart("file") MultipartFile arquivoLeituras) {

        if (arquivoLeituras.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "arquivo vazio");
        }

        Analises analises = null;

        try {
            analises = analisesService.analisarArquivo(arquivoLeituras.getInputStream());
        } catch (Exception e) {
            // TODO: exception response
        }

        return ResponseEntity.ok(new AnalisesResponseDTO(analises.getEpisodios(), analises.getRanking(),
                analises.getLinhasInvalidas(), analises.getResumo()));
    }
}
