package br.com.zup.bootcamp.proposta.controller;

import br.com.zup.bootcamp.proposta.model.Aviso;
import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.model.request.AvisoRequest;
import br.com.zup.bootcamp.proposta.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.service.AvisoViagemService;
import br.com.zup.bootcamp.proposta.service.feign.CartoesCliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/cartoes")
public class AvisoViagemController {

    private final Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);
    private final CartaoRepository cartaoRepository;
    private final AvisoViagemService avisoViagemService;

    public AvisoViagemController(CartaoRepository cartaoRepository, AvisoViagemService avisoViagemService) {
        this.cartaoRepository = cartaoRepository;
        this.avisoViagemService = avisoViagemService;
    }

    @PostMapping(value = "/{idCartao}/avisos")
    public ResponseEntity<?> avisos (@PathVariable UUID idCartao, UriComponentsBuilder builder,
                                             HttpServletRequest request, @RequestBody @Valid AvisoRequest avisoRequest) {

        Optional<Cartao> cartaoBuscado = cartaoRepository.findByNumeroCartao(idCartao);

        if (cartaoBuscado.isEmpty()) {
            logger.warn("[Aviso viagem]: Não foi possível encontrar o cartão [id]: {}", idCartao);
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = cartaoBuscado.get();

        Aviso aviso = avisoViagemService.realizarAviso(cartao, avisoRequest, request);

        return ResponseEntity
                .created(builder.path("/api/avisos/{id}")
                        .buildAndExpand(aviso.getId())
                        .toUri())
                .build();
    }
}
