package br.com.zup.bootcamp.proposta.controller;

import br.com.zup.bootcamp.proposta.advice.ErroPadronizado;
import br.com.zup.bootcamp.proposta.model.Biometria;
import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.model.request.BiometriaRequest;
import br.com.zup.bootcamp.proposta.repository.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/cartoes")
public class BiometriaController {

    private final CartaoRepository cartaoRepository;
    private Logger logger = LoggerFactory.getLogger(Biometria.class);

    public BiometriaController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping(value = "/{idCartao}/biometria")
    public ResponseEntity<?> criarBiometria (@PathVariable UUID idCartao, @RequestBody @Valid BiometriaRequest request,
                                             UriComponentsBuilder builder) {

        Optional<Cartao> cartaoBuscado = cartaoRepository.findById(idCartao);
        Biometria biometria = request.toBiometria();

        if (cartaoBuscado.isEmpty()){
            logger.warn("[Cadastro de biometria]: Não foi possível encontrar o cartão [id]: {}", idCartao);
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = cartaoBuscado.get();

        if (!cartao.verificarSeCartaoEIgualAoEmailToken()){
            logger.warn("[Cadastro de biometria]: O usuário logado não tem permissão para alterar o cartao: {}", idCartao);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErroPadronizado(Collections.singletonList("Cartão não pertencente ao solicitante")));
        }

        cartao.setBiometria(biometria);
        cartaoRepository.save(cartao);

        logger.info("[Cadastro de biometria] - Biometria do Cartão id: {}", cartao.getId());

        return ResponseEntity.created(builder.path("/api/cartoes/{id}").buildAndExpand(cartao.getId()).toUri()).build();
    }
}
