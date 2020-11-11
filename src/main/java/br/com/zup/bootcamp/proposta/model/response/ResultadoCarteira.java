package br.com.zup.bootcamp.proposta.model.response;

import br.com.zup.bootcamp.proposta.model.enums.ResultadoCarteiraStatus;

public class ResultadoCarteira {

    private String id;
    private ResultadoCarteiraStatus resultadoCarteira;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResultadoCarteiraStatus getResultadoCarteira() {
        return resultadoCarteira;
    }

    public void setResultadoCarteira(ResultadoCarteiraStatus resultadoCarteira) {
        this.resultadoCarteira = resultadoCarteira;
    }
}
