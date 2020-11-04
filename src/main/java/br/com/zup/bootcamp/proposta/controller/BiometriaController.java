package br.com.zup.bootcamp.proposta.controller;

import br.com.zup.bootcamp.proposta.model.Biometria;
import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.model.request.BiometriaRequest;
import br.com.zup.bootcamp.proposta.repository.CartaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/cartoes")
public class BiometriaController {

    private final CartaoRepository cartaoRepository;

    public BiometriaController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping(value = "/{idCartao}/biometria")
    public ResponseEntity<?> criarBiometria (@PathVariable UUID idCartao, @RequestBody @Valid BiometriaRequest request,
                                             UriComponentsBuilder builder) {

        Optional<Cartao> cartao = cartaoRepository.findById(idCartao);
        Biometria biometria = request.toBiometria();

        if (cartao.isEmpty()){
            ResponseEntity.badRequest().build();
        }

        cartao.get().setBiometria(biometria);
        cartaoRepository.save(cartao.get());

        return ResponseEntity.created(builder.path("/api/cartoes/{id}")
                .buildAndExpand(cartao.get().getId()).toUri())
                .build();
    }
}
