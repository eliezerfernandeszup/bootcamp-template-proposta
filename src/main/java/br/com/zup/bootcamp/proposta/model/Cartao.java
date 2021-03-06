package br.com.zup.bootcamp.proposta.model;

import br.com.zup.bootcamp.proposta.model.enums.CarteirasDisponiveis;
import br.com.zup.bootcamp.proposta.model.enums.EstadoCartao;
import br.com.zup.bootcamp.proposta.security.JwtUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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

    @Enumerated(EnumType.STRING)
    private EstadoCartao estadoCartao;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Biometria> biometrias = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Bloqueio> bloqueios = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Aviso> avisosViagens = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Carteira> carteiras = new HashSet<>();

    @OneToOne
    private Proposta proposta;

    @Deprecated
    public Cartao(){
    }

    public Cartao(@NotNull UUID numeroCartao, @NotNull LocalDateTime emitidoEm) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.estadoCartao = EstadoCartao.DESBLOQUEADO;
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

    public void setBiometria(Biometria biometria) {
        Assert.notNull(biometria, "A biometria não pode ser nula");
        biometrias.add(biometria);
    }

    public void setBloqueios(Bloqueio bloqueio) {
        Assert.notNull(bloqueio, "O bloqueio não pode ser nula");
        bloqueios.add(bloqueio);
    }

    public void setAvisosViagens(Aviso aviso) {
        Assert.notNull(aviso, "O aviso não pode ser nulo");
        avisosViagens.add(aviso);
    }

    public void setCarteiras(Carteira carteira) {
        Assert.notNull(carteira, "A Carteira não pode ser nulo");
        this.carteiras.add(carteira);
    }

    public void setProposta(Proposta proposta) {
        Assert.notNull(proposta, "A proposta não pode ser nulo");
        this.proposta = proposta;
    }

    public void bloquearCartao() {
        this.estadoCartao = EstadoCartao.BLOQUEADO;
    }

    public boolean verificarSeCartaoEstaBloqueado(){
        return estadoCartao.equals(EstadoCartao.BLOQUEADO);
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

    public boolean verificarSeExisteCarteiraAssociada(CarteirasDisponiveis carteirasDisponivel) {
        Assert.notNull(carteirasDisponivel, "A Carteira não pode ser nula.");
        return carteiras.stream().anyMatch(carteira -> carteira.verificarCarteira(carteirasDisponivel));
    }

    public boolean verificarSeCartaoEIgualAoEmailToken() {
        String userEmail = JwtUtils.getUserEmail();
        return this.proposta.getEmail().equals(userEmail);
    }
}
