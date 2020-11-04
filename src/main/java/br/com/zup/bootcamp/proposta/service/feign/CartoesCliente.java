package br.com.zup.bootcamp.proposta.service.feign;

import br.com.zup.bootcamp.proposta.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartoesCliente", url = "${servicos-externos.cartoes}")
public interface CartoesCliente {

    @GetMapping("/api/cartoes")
    CartaoResponse getAnalise(@RequestParam String idProposta);


}
