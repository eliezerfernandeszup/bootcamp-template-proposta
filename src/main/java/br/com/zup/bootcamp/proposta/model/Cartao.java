package br.com.zup.bootcamp.proposta.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    private UUID numeroCartao;

    @NotNull
    private LocalDateTime emitidoEm;

    @Deprecated
    public Cartao(){
    }

    public Cartao(@NotNull UUID numeroCartao, @NotNull LocalDateTime emitidoEm) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(UUID numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public void setEmitidoEm(LocalDateTime emitidoEm) {
        this.emitidoEm = emitidoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cartao cartao = (Cartao) o;

        if (numeroCartao != null ? !numeroCartao.equals(cartao.numeroCartao) : cartao.numeroCartao != null)
            return false;
        return emitidoEm != null ? emitidoEm.equals(cartao.emitidoEm) : cartao.emitidoEm == null;
    }

    @Override
    public int hashCode() {
        int result = numeroCartao != null ? numeroCartao.hashCode() : 0;
        result = 31 * result + (emitidoEm != null ? emitidoEm.hashCode() : 0);
        return result;
    }
}
