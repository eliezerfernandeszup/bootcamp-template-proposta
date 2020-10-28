package br.com.zup.bootcamp.proposta.validators;

import br.com.zup.bootcamp.proposta.model.Proposta;
import br.com.zup.bootcamp.proposta.repository.PropostaRepository;
import org.springframework.stereotype.Component;

@Component
public class DocumentoUnicoValidator{

    private final PropostaRepository propostaRepository;

    public DocumentoUnicoValidator(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    public boolean isValid(Proposta proposta) {

        final var existeDocumento= propostaRepository.findByDocumento(proposta.getDocumento());

        return existeDocumento.isEmpty();
    }

}
