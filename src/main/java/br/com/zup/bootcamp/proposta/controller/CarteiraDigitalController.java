package br.com.zup.bootcamp.proposta.controller;

import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.model.Carteira;
import br.com.zup.bootcamp.proposta.model.request.CarteiraRequest;
import br.com.zup.bootcamp.proposta.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.service.CarteiraDigitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/cartoes")
public class CarteiraDigitalController {

    private final CarteiraDigitalService carteiraDigitalService;
    private final CartaoRepository cartaoRepository;
    private final Logger logger = LoggerFactory.getLogger(CarteiraDigitalController.class);

    public CarteiraDigitalController(CarteiraDigitalService carteiraDigitalService, CartaoRepository cartaoRepository) {
        this.carteiraDigitalService = carteiraDigitalService;
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping(value = "/{idCartao}/carteiras")
    public ResponseEntity<?> associarCarteira (@PathVariable UUID idCartao, UriComponentsBuilder builder,
                                                    @RequestBody @Valid CarteiraRequest carteiraRequest) {

        Optional<Cartao> cartaoBuscado = cartaoRepository.findByNumeroCartao(idCartao);

        if (cartaoBuscado.isEmpty()) {
            logger.warn("[Carteira Digital]: Não foi possível encontrar o cartão [id]: {}", idCartao);
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = cartaoBuscado.get();

        if (cartao.verificarSeExisteCarteiraAssociada(carteiraRequest.getCarteira())){
            logger.warn("[Carteira Digital]: Não foi possível encontrar o cartão [id]: {}", idCartao);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("O Cartão já está associado com a carteira");
        }

        return carteiraDigitalService.adicionarCarteiraDigital(cartao, carteiraRequest, builder);
    }
}
