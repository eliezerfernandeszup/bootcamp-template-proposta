package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.model.Proposta;
import br.com.zup.bootcamp.proposta.repository.PropostaRepository;
import br.com.zup.bootcamp.proposta.validators.DocumentoUnicoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final DocumentoUnicoValidator valorUnicoValidator;

    public PropostaService(PropostaRepository propostaRepository, DocumentoUnicoValidator valorUnicoValidator) {
        this.propostaRepository = propostaRepository;
        this.valorUnicoValidator = valorUnicoValidator;
    }

    public Proposta criar(Proposta proposta) {

        if (!valorUnicoValidator.isValid(proposta)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final var propostaSalva = this.propostaRepository.save(proposta);

        return propostaSalva;
    }
}
