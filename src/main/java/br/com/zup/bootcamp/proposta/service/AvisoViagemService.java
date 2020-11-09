package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.model.Aviso;
import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.model.request.AvisoRequest;
import br.com.zup.bootcamp.proposta.model.response.ResultadoAvisoViagem;
import br.com.zup.bootcamp.proposta.repository.AvisoRepository;
import br.com.zup.bootcamp.proposta.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.service.feign.CartoesCliente;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@Service
public class AvisoViagemService {

    private final CartoesCliente cartoesCliente;
    private final CartaoRepository cartaoRepository;
    private final AvisoRepository avisoRepository;
    private final Logger logger = LoggerFactory.getLogger(AvisoViagemService.class);

    public AvisoViagemService(CartoesCliente cartoesCliente, CartaoRepository cartaoRepository, AvisoRepository avisoRepository) {
        this.cartoesCliente = cartoesCliente;
        this.cartaoRepository = cartaoRepository;
        this.avisoRepository = avisoRepository;
    }

    public Aviso realizarAviso(Cartao cartao, @Valid AvisoRequest avisoRequest, HttpServletRequest request){

        if (cartao.verificarSeCartaoEstaBloqueado()) {
            logger.warn("[Aviso Viagem]: Não é possível adicionar aviso em um cartão bloqueado. Cartão: {}", cartao.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cartão bloqueado!");
        }

        Aviso avisoViagem = null;

        try {
            final var response = cartoesCliente.enviarAvisoDeViagem(cartao.getNumeroCartao(), avisoRequest);

            if (response.getBody() == ResultadoAvisoViagem.CRIADO) {

                avisoViagem = avisoRequest.toAviso(request);
                avisoRepository.save(avisoViagem);
                logger.info("[Aviso Viagem]: Salvando aviso: {}", avisoViagem.getId());

                cartao.setAvisosViagens(avisoViagem);
                cartaoRepository.save(cartao);
                logger.warn("[Aviso Viagem]: Associando aviso ao cartão id: {}", cartao.getId());
            }
        }catch (FeignException exception) {
            logger.warn("[Aviso Viagem]: Não foi possível criar aviso para o cartão: {}", exception.contentUTF8());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possível criar o aviso para o cartão");
        }

        return avisoViagem;
    }

}
