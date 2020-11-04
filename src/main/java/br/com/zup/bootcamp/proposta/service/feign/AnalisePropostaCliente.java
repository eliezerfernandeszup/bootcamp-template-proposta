package br.com.zup.bootcamp.proposta.service.feign;

import br.com.zup.bootcamp.proposta.model.request.AnalisePropostaRequest;
import br.com.zup.bootcamp.proposta.model.response.ResultadoAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analiseProposta", url = "${servicos-externos.base-url}")
public interface AnalisePropostaCliente {

    @PostMapping("/api/solicitacao")
    ResponseEntity<ResultadoAnaliseResponse> getAnalise(@RequestBody AnalisePropostaRequest request);
}
