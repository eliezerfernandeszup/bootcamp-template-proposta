package br.com.zup.bootcamp.proposta.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RecuperarSenha {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotBlank
    private String ipDoCliente;

    @NotBlank
    private String userAgent;

    @CreationTimestamp
    private LocalDateTime instanteDaSolicitacao;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public RecuperarSenha(){
    }

    public RecuperarSenha(@NotBlank String ipDoCliente, @NotBlank String userAgent, Cartao cartao) {
        this.ipDoCliente = ipDoCliente;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public UUID getId() {
        return id;
    }
}
