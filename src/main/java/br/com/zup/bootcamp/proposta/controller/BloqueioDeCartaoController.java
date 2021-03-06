package br.com.zup.bootcamp.proposta.controller;

import br.com.zup.bootcamp.proposta.advice.ErroPadronizado;
import br.com.zup.bootcamp.proposta.model.Biometria;
import br.com.zup.bootcamp.proposta.model.Bloqueio;
import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.service.BloqueioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/cartoes")
public class BloqueioDeCartaoController {

    private Logger logger = LoggerFactory.getLogger(Biometria.class);
    private final CartaoRepository cartaoRepository;
    private final BloqueioService bloqueioService;

    public BloqueioDeCartaoController(CartaoRepository cartaoRepository, BloqueioService bloqueioService) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioService = bloqueioService;
    }

    @PostMapping(value = "/{idCartao}/bloqueios")
    public ResponseEntity<?> bloquearCartao (@PathVariable UUID idCartao, UriComponentsBuilder builder ,
                                             HttpServletRequest request) {

        Optional<Cartao> cartaoBuscado = cartaoRepository.findByNumeroCartao(idCartao);

        if (cartaoBuscado.isEmpty()) {
            logger.warn("[Bloqueio de cartão]: Não foi possível encontrar o cartão [id]: {}", idCartao);
            return ResponseEntity.notFound().build();
        }

        Bloqueio bloqueio = new Bloqueio(request);
        Cartao cartao = cartaoBuscado.get();

        if (!cartao.verificarSeCartaoEIgualAoEmailToken()){
            logger.warn("[Bloqueio de cartão]: O usuário logado não tem permissão para bloquear o cartao: {}", idCartao);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErroPadronizado(Collections.singletonList("Cartão não pertencente ao solicitante")));
        }

        bloqueioService.processarBloqueio(cartao);

        cartao.setBloqueios(bloqueio);
        cartaoRepository.save(cartao);

        return ResponseEntity.created(builder.path("/api/bloqueios/{id}").buildAndExpand(bloqueio.getId()).toUri()).build();
    }
}
