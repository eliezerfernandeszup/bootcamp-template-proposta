package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.controller.BiometriaController;
import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.model.request.BloqueioRequest;
import br.com.zup.bootcamp.proposta.model.response.CartaoResponse;
import br.com.zup.bootcamp.proposta.service.feign.CartoesCliente;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        }

        Map bloqueioRequest = new HashMap();
        bloqueioRequest.put("sistemaResponsavel", nomeDaAplicacao);


        ResponseEntity bloqueioResponse = cartoesCliente.bloquearCartao(cartao.getNumeroCartao(), bloqueioRequest);

        if (bloqueioResponse.getStatusCode().is2xxSuccessful()) {
            cartao.bloquearCartao();
            logger.warn("[Bloqueio de cartão]: Cartão Bloqueado. Cartão: {}", cartao.getId());
        }
    }
}
