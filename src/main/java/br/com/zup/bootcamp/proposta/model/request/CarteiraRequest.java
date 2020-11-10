package br.com.zup.bootcamp.proposta.model.request;

import br.com.zup.bootcamp.proposta.model.enums.CarteirasDisponiveis;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraRequest {

    @Email
    @NotBlank
    private String email;

    @Enumerated(EnumType.STRING)
    private CarteirasDisponiveis carteira;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CarteirasDisponiveis getCarteira() {
        return carteira;
    }

    public void setCarteira(CarteirasDisponiveis carteira) {
        this.carteira = carteira;
    }

    @Override
    public String toString() {
        return "CarteiraRequest{" +
                "email='" + email + '\'' +
                ", carteira=" + carteira +
                '}';
    }
}
