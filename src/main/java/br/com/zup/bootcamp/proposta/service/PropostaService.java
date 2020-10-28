package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.model.Proposta;
import br.com.zup.bootcamp.proposta.repository.PropostaRepository;
import org.springframework.stereotype.Service;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;

    public PropostaService(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    public Proposta criar(Proposta proposta) {

        final var propostaSalva = this.propostaRepository.save(proposta);

        return propostaSalva;
    }
}
