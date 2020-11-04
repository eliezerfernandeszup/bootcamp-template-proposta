package br.com.zup.bootcamp.proposta.service;

import br.com.zup.bootcamp.proposta.model.request.AnalisePropostaRequest;
import br.com.zup.bootcamp.proposta.model.response.ResultadoAnaliseResponse;
import br.com.zup.bootcamp.proposta.service.feign.AnalisePropostaCliente;
import com.google.gson.Gson;
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

    public ResultadoAnaliseResponse avaliar(AnalisePropostaRequest request) {

        logger.info("[Análise financeira] : Realizando análise da proposta: {}", request.getIdProposta());

        try {
            analiseResponse = this.analisePropostaCliente.getAnalise(request).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()){
                analiseResponse = new Gson().fromJson(e.contentUTF8(), ResultadoAnaliseResponse.class);
            }
        }
        Assert.notNull(analiseResponse, "Não foi possíve realizar a análise.");

        return analiseResponse;
    }
}
