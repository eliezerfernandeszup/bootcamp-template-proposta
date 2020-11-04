package br.com.zup.bootcamp.proposta.model.request;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class AnalisePropostaRequest {

    @NotBlank
    private String documento;

    @NotBlank
    private String nome;

    @NotBlank
    private UUID idProposta;

    public AnalisePropostaRequest(@NotBlank String documento, @NotBlank String nome, UUID idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public UUID getIdProposta() {
        return idProposta;
    }

    @Override
    public String toString() {
        return "AnalisePropostaRequest{" +
                "documento='" + documento + '\'' +
                ", nome='" + nome + '\'' +
                ", idProposta='" + idProposta + '\'' +
                '}';
    }
}
