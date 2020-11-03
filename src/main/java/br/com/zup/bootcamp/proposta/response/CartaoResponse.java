package br.com.zup.bootcamp.proposta.response;

import br.com.zup.bootcamp.proposta.model.Cartao;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class CartaoResponse {

    @NotNull
    private UUID id;

    private LocalDateTime emitidoEm;

    private String idProposta;

    public Cartao toCartao() {
        return new Cartao(this.id, this.emitidoEm);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public void setEmitidoEm(LocalDateTime emitidoEm) {
        this.emitidoEm = emitidoEm;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }
}
