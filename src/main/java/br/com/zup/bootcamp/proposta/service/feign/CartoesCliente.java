package br.com.zup.bootcamp.proposta.service.feign;

import br.com.zup.bootcamp.proposta.model.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "cartoesCliente", url = "${servicos-externos.cartoes}")
public interface CartoesCliente {

    @GetMapping("/api/cartoes")
    CartaoResponse buscarCartaoPorIdProposta(@RequestParam String idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    ResponseEntity<CartaoResponse> bloquearCartao(@PathVariable UUID id, @RequestBody Map sistemaResponsavel);


}
