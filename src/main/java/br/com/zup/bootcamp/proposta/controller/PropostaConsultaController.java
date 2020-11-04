package br.com.zup.bootcamp.proposta.controller;

import br.com.zup.bootcamp.proposta.model.Proposta;
import br.com.zup.bootcamp.proposta.repository.PropostaRepository;
import br.com.zup.bootcamp.proposta.response.PropostaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/propostas")
public class PropostaConsultaController {

    private final PropostaRepository propostaRepository;

    public PropostaConsultaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> buscarProposta (@PathVariable("id") UUID id) {

        Optional<Proposta> proposta = propostaRepository.findById(id);

        if (proposta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PropostaResponse propostaResponse = new PropostaResponse(proposta.get());

        return ResponseEntity.ok(propostaResponse);
    }

}
