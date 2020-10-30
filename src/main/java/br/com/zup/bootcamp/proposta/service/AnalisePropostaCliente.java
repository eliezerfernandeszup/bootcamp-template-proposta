package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.request.AnalisePropostaRequest;
import br.com.zup.bootcamp.proposta.response.ResultadoAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "analiseProposta", url = "${servicos-externos.base-url}")
public interface AnalisePropostaCliente {

    @PostMapping("/api/solicitacao")
    ResponseEntity<ResultadoAnaliseResponse> getAnalise(AnalisePropostaRequest request);
}
