package br.com.zup.bootcamp.proposta.model;

import br.com.zup.bootcamp.proposta.annotations.CpfOuCnpj;
import br.com.zup.bootcamp.proposta.model.enums.PropostaStatus;
import br.com.zup.bootcamp.proposta.model.request.AnalisePropostaRequest;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

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
    private PropostaStatus propostaStatus;

    @NotNull
    private boolean cartaoCriado;

    @OneToOne(cascade = CascadeType.ALL)
    private Cartao cartao;

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
        this.propostaStatus = PropostaStatus.PENDENTE;
        this.cartaoCriado = false;
    }

    public UUID getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
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

    public Cartao getCartao() {
        return cartao;
    }

    public boolean isCartaoCriado() {
        return cartaoCriado;
    }

    public void setCartaoCriado(boolean cartaoCriado) {
        this.cartaoCriado = cartaoCriado;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "documento='" + documento + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salario=" + salario +
                ", propostaStatus=" + propostaStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Proposta proposta = (Proposta) o;

        if (id != null ? !id.equals(proposta.id) : proposta.id != null) return false;
        return cartao != null ? cartao.equals(proposta.cartao) : proposta.cartao == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cartao != null ? cartao.hashCode() : 0);
        return result;
    }

    public AnalisePropostaRequest toAnalisePropostaRequest() {
        return new AnalisePropostaRequest(this.documento, this.nome, this.id);
    }

    public void atualizarStatus(PropostaStatus status) {
        this.propostaStatus = status;
    }
}
