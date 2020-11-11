package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.model.Carteira;
import br.com.zup.bootcamp.proposta.model.request.CarteiraRequest;
import br.com.zup.bootcamp.proposta.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.repository.CarteiraRepository;
import br.com.zup.bootcamp.proposta.service.feign.CartoesCliente;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Service
public class CarteiraDigitalService {

    private final Logger logger = LoggerFactory.getLogger(CarteiraDigitalService.class);
    private final CartoesCliente cartoesCliente;
    private final CarteiraRepository carteiraRepository;
    private final CartaoRepository cartaoRepository;

    public CarteiraDigitalService(CartoesCliente cartoesCliente, CarteiraRepository carteiraRepository,
                                  CartaoRepository cartaoRepository) {
        this.cartoesCliente = cartoesCliente;
        this.carteiraRepository = carteiraRepository;
        this.cartaoRepository = cartaoRepository;
    }

    public ResponseEntity<?> adicionarCarteiraDigital(Cartao cartao, @Valid CarteiraRequest carteiraRequest, UriComponentsBuilder builder){
        Carteira carteira = new Carteira(carteiraRequest.getEmail(), carteiraRequest.getCarteira());

        try {
            final var response = cartoesCliente.adicionarCarteira(cartao.getNumeroCartao(), carteiraRequest);

            if (response.getStatusCode().is2xxSuccessful()) {

                carteiraRepository.save(carteira);
                logger.info("[Carteira Digital]: Salvando carteira digital: {}", carteira.getId());

                cartao.setCarteiras(carteira);
                cartaoRepository.save(cartao);
                logger.info("[Carteira Digital]: Associando carteira ao cartão id: {}", cartao.getId());
            }
        }catch (FeignException exception){
            logger.warn(exception.contentUTF8());
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        return ResponseEntity
                .created(builder.path("/api/carteiras/")
                        .buildAndExpand(carteira.getId())
                        .toUri())
                .build();
    }
}
