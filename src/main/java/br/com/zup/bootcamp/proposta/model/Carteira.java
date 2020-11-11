package br.com.zup.bootcamp.proposta.model;

import br.com.zup.bootcamp.proposta.model.enums.CarteirasDisponiveis;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Email
    @NotBlank
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CarteirasDisponiveis carteira;

    @Deprecated
    public Carteira(){
    }

    public Carteira(@Email @NotBlank String email, @NotNull CarteirasDisponiveis carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public UUID getId() {
        return id;
    }

    public boolean verificarCarteira(CarteirasDisponiveis carteirasDisponivel) {
        Assert.notNull(carteirasDisponivel, "A carteira não pode ser nula");
        return this.carteira.equals(carteirasDisponivel);
    }
}
