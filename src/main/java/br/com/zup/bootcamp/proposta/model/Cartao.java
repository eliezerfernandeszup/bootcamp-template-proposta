package br.com.zup.bootcamp.proposta.model;

import java.time.LocalDateTime;

public class Cartao {
     private String id;
     private LocalDateTime emitidoEm;
     private String titular;
     private String idProposta;

    public Cartao(String id, LocalDateTime emitidoEm, String titular, String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.idProposta = idProposta;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public String getIdProposta() {
        return idProposta;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                ", emitidoEm=" + emitidoEm +
                ", titular='" + titular + '\'' +
                ", idProposta='" + idProposta + '\'' +
                '}';
    }
}
