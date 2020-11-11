package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.model.Proposta;
import br.com.zup.bootcamp.proposta.model.enums.PropostaStatus;
import br.com.zup.bootcamp.proposta.model.response.CartaoResponse;
import br.com.zup.bootcamp.proposta.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.repository.PropostaRepository;
import br.com.zup.bootcamp.proposta.service.feign.CartoesCliente;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    private final CartoesCliente cartoesCliente;
    private final PropostaRepository propostaRepository;
    private final CartaoRepository cartaoRepository;
    private final Logger logger = LoggerFactory.getLogger(CartaoService.class);

    public CartaoService(CartoesCliente cartoesCliente, PropostaRepository propostaRepository, CartaoRepository cartaoRepository) {
        this.cartoesCliente = cartoesCliente;
        this.propostaRepository = propostaRepository;
        this.cartaoRepository = cartaoRepository;
    }

    @Async("cartaoAsync")
    public void verificarCartao(){
        logger.info("Scheduled: verificando se existe cartao na proposta" );

        var propostas = propostaRepository.findByPropostaStatusAndCartaoCriado(PropostaStatus.ELEGIVEL, false);

        for (Proposta proposta : propostas) {
            try {
                CartaoResponse response = cartoesCliente.buscarCartaoPorIdProposta(proposta.toAnalisePropostaRequest().getIdProposta().toString());

                Cartao cartao = response.toCartao();
                cartao.setProposta(proposta);
                cartaoRepository.save(cartao);

                proposta.atualizarStatus(PropostaStatus.CONCLUIDA);
                proposta.setCartaoCriado(true);
                proposta.setCartao(cartao);

                propostaRepository.save(proposta);

                logger.info("Scheduled: --- Cartão criado: {}", proposta.getCartao().getId());

            } catch (FeignException e) {
                logger.warn(e.contentUTF8());
            }
        }
    }
}
