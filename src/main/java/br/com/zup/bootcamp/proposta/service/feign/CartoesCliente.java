package br.com.zup.bootcamp.proposta.service.feign;

import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.request.AnalisePropostaRequest;
import br.com.zup.bootcamp.proposta.response.ResultadoAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cartoesCliente", url = "${servicos-externos.cartoes}")
public interface CartoesCliente {

    @PostMapping("/api/cartoes")
    ResponseEntity<Cartao> getAnalise(AnalisePropostaRequest request);


}
