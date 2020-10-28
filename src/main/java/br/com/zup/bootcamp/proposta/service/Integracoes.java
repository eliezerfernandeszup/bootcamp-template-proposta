package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.request.AnalisePropostaRequest;
import org.springframework.web.bind.annotation.PostMapping;

public interface Integracoes {

    @PostMapping("/api/solicitacao")
    public String getAnalisa(AnalisePropostaRequest request);
}
