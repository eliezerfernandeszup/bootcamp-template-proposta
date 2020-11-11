package br.com.zup.bootcamp.proposta.controller;

import br.com.zup.bootcamp.proposta.advice.ErroPadronizado;
import br.com.zup.bootcamp.proposta.model.Biometria;
import br.com.zup.bootcamp.proposta.model.Cartao;
import br.com.zup.bootcamp.proposta.model.RecuperarSenha;
import br.com.zup.bootcamp.proposta.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.repository.RecuperarSenhaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
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
public class RecuperarSenhaController {

    private Logger logger = LoggerFactory.getLogger(Biometria.class);
    private final RecuperarSenhaRepository recuperarSenhaRepository;
    private final CartaoRepository cartaoRepository;

    public RecuperarSenhaController(RecuperarSenhaRepository recuperarSenhaRepository, CartaoRepository cartaoRepository) {
        this.recuperarSenhaRepository = recuperarSenhaRepository;
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping(value = "/{idCartao}/recuperar-senha")
    public ResponseEntity<?> recuperarSenha (@PathVariable UUID idCartao, UriComponentsBuilder builder ,
                                             HttpServletRequest request) {

        Optional<Cartao> cartaoBuscado = cartaoRepository.findByNumeroCartao(idCartao);

        if (cartaoBuscado.isEmpty()) {
            logger.warn("[Recuperar Senha]: Não foi possível encontrar o cartão [id]: {}", idCartao);
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = cartaoBuscado.get();

        if (!cartao.verificarSeCartaoEIgualAoEmailToken()){
            logger.warn("[Recuperar Senha]: O usuário logado não tem permissão solicitar recuperação de senha: {}", cartao.getId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErroPadronizado(Collections.singletonList("Cartão não pertencente ao solicitante")));
        }

        if (cartao.verificarSeCartaoEstaBloqueado()) {
            logger.warn("[Recuperar Senha]: O cartão está bloqueado:");
            return ResponseEntity.badRequest().body("Cartão bloqueado");
        }

        RecuperarSenha recuperarSenha = new RecuperarSenha(request, cartao);
        recuperarSenhaRepository.save(recuperarSenha);

        logger.info("[Recuperar Senha]: Recuperação de senha cadastrada [id]: {}", recuperarSenha.getId());

        return ResponseEntity
                .created(builder.path("/api/cartoes/recuperar-senha/{id}")
                        .buildAndExpand(recuperarSenha.getId())
                        .toUri())
                .build();
    }
}
