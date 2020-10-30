package br.com.zup.bootcamp.proposta.response;

import br.com.zup.bootcamp.proposta.enums.AnalisePropostaStatus;

public class ResultadoAnaliseResponse {

    private String documento;
    private String nome;
    private AnalisePropostaStatus resultadoSolicitacao;
    private String idProposta;

    @Deprecated
    public ResultadoAnaliseResponse(){}

    public ResultadoAnaliseResponse(String documento, AnalisePropostaStatus resultadoSolicitacao, String nome, String idProposta) {
        this.documento = documento;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public AnalisePropostaStatus getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public void setResultadoSolicitacao(AnalisePropostaStatus resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }
}
