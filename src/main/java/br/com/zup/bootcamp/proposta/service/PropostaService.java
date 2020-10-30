package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.enums.AnalisePropostaStatus;
import br.com.zup.bootcamp.proposta.model.Proposta;
import br.com.zup.bootcamp.proposta.repository.PropostaRepository;
import br.com.zup.bootcamp.proposta.validators.DocumentoUnicoValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final DocumentoUnicoValidator valorUnicoValidator;
    private final AvaliaProposta avaliaProposta;

    private final Logger log = LoggerFactory.getLogger(PropostaService.class);

    public PropostaService(PropostaRepository propostaRepository, DocumentoUnicoValidator valorUnicoValidator,
                           AvaliaProposta avaliaProposta) {
        this.propostaRepository = propostaRepository;
        this.valorUnicoValidator = valorUnicoValidator;
        this.avaliaProposta = avaliaProposta;
    }

    public Proposta criar(Proposta proposta) throws JsonProcessingException {

        if (!valorUnicoValidator.isValid(proposta)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final var propostaSalva = this.propostaRepository.save(proposta);
        final var analisePropostaRequest = proposta.toAnalisePropostaRequest();

        AnalisePropostaStatus avaliacao = this.avaliaProposta.avaliar(analisePropostaRequest).getResultadoSolicitacao();

        propostaSalva.atualizarStatus(avaliacao.toPropostaStatus());
        this.propostaRepository.save(propostaSalva);

        return propostaSalva;
    }
}
