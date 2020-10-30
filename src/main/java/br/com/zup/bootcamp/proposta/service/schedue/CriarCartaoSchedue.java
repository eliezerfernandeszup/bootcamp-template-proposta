package br.com.zup.bootcamp.proposta.service.schedue;

import br.com.zup.bootcamp.proposta.enums.PropostaStatus;
import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.model.Proposta;
import br.com.zup.bootcamp.proposta.repository.PropostaRepository;
import br.com.zup.bootcamp.proposta.request.AnalisePropostaRequest;
import br.com.zup.bootcamp.proposta.service.feign.CartoesCliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CriarCartaoSchedue {

    private final CartoesCliente cartoesCliente;
    private final PropostaRepository propostaRepository;
    private final Logger logger = LoggerFactory.getLogger(CriarCartaoSchedue.class);

    public CriarCartaoSchedue(CartoesCliente cartoesCliente, PropostaRepository propostaRepository) {
        this.cartoesCliente = cartoesCliente;
        this.propostaRepository = propostaRepository;
    }

    @Scheduled(fixedDelayString = "${periodicidade-schedue.criar-cartao}")
    public void criarCartao(){
        logger.info("Scheduled: verificando se existe cartao na proposta" );

        List<Proposta> propostasElegiveis = propostaRepository.findByResultadoPropostaStatus(PropostaStatus.ELEGIVEL);

        List<AnalisePropostaRequest> analiseProposta = propostasElegiveis
                .stream().map(proposta -> proposta.toAnalisePropostaRequest())
                .collect(Collectors.toList());

        analiseProposta.forEach( analisePropostaRequest -> {
                ResponseEntity<Cartao> cartaoCriado = cartoesCliente.getAnalise(analisePropostaRequest);

                logger.info("Scheduled: Status code: {}  --- Cartão criado: {}",
                        cartaoCriado.getStatusCode(),
                        cartaoCriado.getHeaders().getLocation());
            }
        );

    }
}
