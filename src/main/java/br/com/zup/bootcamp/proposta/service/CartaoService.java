package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.enums.PropostaStatus;
import br.com.zup.bootcamp.proposta.model.Proposta;
import br.com.zup.bootcamp.proposta.repository.PropostaRepository;
import br.com.zup.bootcamp.proposta.response.CartaoResponse;
import br.com.zup.bootcamp.proposta.service.feign.CartoesCliente;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CartaoService {

    private final CartoesCliente cartoesCliente;
    private final PropostaRepository propostaRepository;
    private final Logger logger = LoggerFactory.getLogger(CartaoService.class);

    public CartaoService(CartoesCliente cartoesCliente, PropostaRepository propostaRepository) {
        this.cartoesCliente = cartoesCliente;
        this.propostaRepository = propostaRepository;
    }

    @Async("cartaoAsync")
    public void verificarCartao(){
        logger.info("Scheduled: verificando se existe cartao na proposta" );

        Collection<Proposta> propostas = propostaRepository.findByPropostaStatus(PropostaStatus.ELEGIVEL);

        propostas.forEach( proposta -> {
                try {
                    CartaoResponse response = cartoesCliente.getAnalise(proposta.toAnalisePropostaRequest().getIdProposta());

                    proposta.atualizarStatus(PropostaStatus.CONCLUIDA);
                    proposta.setCartaoCriado(true);
                    proposta.setCartao(response.toCartao());

                    propostaRepository.save(proposta);

                    logger.info("Scheduled: --- Cartão criado: {}", response.getIdProposta());

                }catch (FeignException e) {
                    logger.warn(e.contentUTF8());
                }
            }
        );

    }
}
