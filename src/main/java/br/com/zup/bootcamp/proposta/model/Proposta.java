package br.com.zup.bootcamp.proposta.model;

import br.com.zup.bootcamp.proposta.annotations.CpfOuCnpj;
import br.com.zup.bootcamp.proposta.enums.PropostaStatus;
import br.com.zup.bootcamp.proposta.request.AnalisePropostaRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    @CpfOuCnpj(fieldName = "documento", domainClass = Proposta.class)
    private String documento;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PropostaStatus resultadoPropostaStatus;
    
    @Deprecated
    public Proposta(){
    }

    public Proposta(@NotBlank String documento, @NotBlank String nome, @NotBlank @Email String email,
                    @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
        this.resultadoPropostaStatus = PropostaStatus.PENDENTE;
    }

    public String getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "documento='" + documento + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salario=" + salario +
                ", resultadoPropostaStatus=" + resultadoPropostaStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Proposta proposta = (Proposta) o;

        return id != null ? id.equals(proposta.id) : proposta.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public AnalisePropostaRequest toAnalisePropostaRequest() {
        return new AnalisePropostaRequest(this.documento, this.nome, this.id);
    }

    public void atualizarStatus(PropostaStatus status) {
        this.resultadoPropostaStatus = status;
    }
}
