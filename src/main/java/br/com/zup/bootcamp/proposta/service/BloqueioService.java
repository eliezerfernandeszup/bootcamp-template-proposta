package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.model.request.BloqueioRequest;
import br.com.zup.bootcamp.proposta.service.feign.CartoesCliente;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BloqueioService {

    private final CartoesCliente cartoesCliente;
    private Logger logger = LoggerFactory.getLogger(BloqueioService.class);

    public BloqueioService(CartoesCliente cartoesCliente) {
        this.cartoesCliente = cartoesCliente;
    }
    @Value("${spring.application.name}")
    private String nomeDaAplicacao;

    public void processarBloqueio(Cartao cartao){

        if (cartao.verificarSeCartaoEstaBloqueado()) {
            logger.warn("[Bloqueio de cartão]: O cartão já está bloqueado. Cartão: {}", cartao.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        BloqueioRequest bloqueioRequest = new BloqueioRequest(nomeDaAplicacao);

        try {
            final var bloqueioResponse = cartoesCliente.bloquearCartao(cartao.getNumeroCartao(), bloqueioRequest);

            if (bloqueioResponse.getStatusCode().is2xxSuccessful()) {
                cartao.bloquearCartao();
                logger.info("[Bloqueio de cartão]: Cartão Bloqueado. Cartão: {}", cartao.getNumeroCartao());
            }
        }catch (FeignException exception) {
            logger.warn("[Bloqueio de cartão]: Não foi possível bloquear o cartão: {}", exception.contentUTF8());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
