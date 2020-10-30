package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.request.AnalisePropostaRequest;
import br.com.zup.bootcamp.proposta.response.ResultadoAnaliseResponse;
import br.com.zup.bootcamp.proposta.service.feign.AnalisePropostaCliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AvaliaProposta {

    private final AnalisePropostaCliente analisePropostaCliente;
    private final Logger logger = LoggerFactory.getLogger(AvaliaProposta.class);
    private ResultadoAnaliseResponse analiseResponse;

    public AvaliaProposta(AnalisePropostaCliente analisePropostaCliente) {
        this.analisePropostaCliente = analisePropostaCliente;
    }

    public ResultadoAnaliseResponse avaliar(AnalisePropostaRequest request) throws JsonProcessingException {

        logger.info("[Análise financeira] : Realizando análise da proposta: {}", request.getIdProposta());

        try {
            analiseResponse = this.analisePropostaCliente.getAnalise(request).getBody();
        } catch (FeignException e) {
            if (HttpStatus.UNPROCESSABLE_ENTITY.equals(e.status())){
                analiseResponse = new ObjectMapper().readValue(e.contentUTF8(), ResultadoAnaliseResponse.class);
            }
        }
        Assert.notNull(analiseResponse, "Não foi possíve realizar a análise.");

        return analiseResponse;
    }
}
