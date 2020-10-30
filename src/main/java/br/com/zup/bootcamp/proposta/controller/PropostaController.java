package br.com.zup.bootcamp.proposta.controller;

import br.com.zup.bootcamp.proposta.request.PropostaRequest;
import br.com.zup.bootcamp.proposta.service.PropostaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/propostas")
public class PropostaController {

    private final PropostaService propostaService;

    public PropostaController(PropostaService propostaService) {
        this.propostaService = propostaService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> criar (@Valid @RequestBody PropostaRequest request,
                                    UriComponentsBuilder builder) throws JsonProcessingException {

        final var proposta = propostaService.criar(request.toModel());

        return ResponseEntity.created(builder.path("/api/propostas/{id}")
                .buildAndExpand(proposta.getId()).toUri())
                .build();
    }

}
