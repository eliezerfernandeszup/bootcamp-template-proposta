package br.com.zup.bootcamp.proposta.service.feign;

import br.com.zup.bootcamp.proposta.model.request.AvisoRequest;
import br.com.zup.bootcamp.proposta.model.request.BloqueioRequest;
import br.com.zup.bootcamp.proposta.model.request.CarteiraRequest;
import br.com.zup.bootcamp.proposta.model.response.ResultadoAvisoViagem;
import br.com.zup.bootcamp.proposta.model.response.CartaoResponse;
import br.com.zup.bootcamp.proposta.model.response.ResultadoCarteira;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "cartoesCliente", url = "${servicos-externos.cartoes}")
public interface CartoesCliente {

    @GetMapping("/api/cartoes")
    CartaoResponse buscarCartaoPorIdProposta(@RequestParam String idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    ResponseEntity<CartaoResponse> bloquearCartao(@PathVariable UUID id, @RequestBody BloqueioRequest sistemaResponsavel);

    @PostMapping("/api/cartoes/{idCartao}/avisos")
    ResponseEntity<ResultadoAvisoViagem> enviarAvisoDeViagem(@PathVariable UUID idCartao, @RequestBody AvisoRequest avisoRequest);

    @PostMapping("/api/cartoes/{idCartao}/carteiras")
    ResponseEntity<ResultadoCarteira> adicionarCarteira(@PathVariable UUID idCartao, @RequestBody CarteiraRequest carteiraRequest);

}
