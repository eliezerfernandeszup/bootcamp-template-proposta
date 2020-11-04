package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.enums.AnalisePropostaStatus;
import br.com.zup.bootcamp.proposta.model.Proposta;
import br.com.zup.bootcamp.proposta.repository.PropostaRepository;
import br.com.zup.bootcamp.proposta.validators.DocumentoUnicoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final DocumentoUnicoValidator valorUnicoValidator;
    private final AvaliaProposta avaliaProposta;

    public PropostaService(PropostaRepository propostaRepository, DocumentoUnicoValidator valorUnicoValidator,
                           AvaliaProposta avaliaProposta) {
        this.propostaRepository = propostaRepository;
        this.valorUnicoValidator = valorUnicoValidator;
        this.avaliaProposta = avaliaProposta;
    }

    public Proposta criar(Proposta proposta) {

        if (!valorUnicoValidator.isValid(proposta)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível continuar com a solicitação");
        }

        final var propostaSalva = this.propostaRepository.save(proposta);
        final var analisePropostaRequest = propostaSalva.toAnalisePropostaRequest();

        AnalisePropostaStatus avaliacao = this.avaliaProposta.avaliar(analisePropostaRequest).getResultadoSolicitacao();

        propostaSalva.atualizarStatus(avaliacao.toPropostaStatus());
        this.propostaRepository.save(propostaSalva);

        return propostaSalva;
    }
}
