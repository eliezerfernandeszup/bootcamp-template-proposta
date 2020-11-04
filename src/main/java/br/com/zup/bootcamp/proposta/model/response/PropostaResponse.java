package br.com.zup.bootcamp.proposta.model.response;

import br.com.zup.bootcamp.proposta.model.enums.PropostaStatus;
import br.com.zup.bootcamp.proposta.model.Proposta;

import java.math.BigDecimal;
import java.util.UUID;

public class PropostaResponse {

    private UUID id;
    private String nome;
    private String email;
    private String endereco;
    private BigDecimal salario;
    private PropostaStatus propostaStatus;
    private boolean cartaoCriado;

    public PropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.nome = proposta.getNome();
        this.email = proposta.getEmail();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
        this.propostaStatus = proposta.getPropostaStatus();
        this.cartaoCriado = proposta.isCartaoCriado();
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public PropostaStatus getPropostaStatus() {
        return propostaStatus;
    }

    public boolean isCartaoCriado() {
        return cartaoCriado;
    }
}
