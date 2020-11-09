package br.com.zup.bootcamp.proposta.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Aviso {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate validoAte;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @CreationTimestamp
    private LocalDateTime instante;

    @Deprecated
    public Aviso() {
    }

    public Aviso(@NotBlank String destino, @NotNull LocalDate validoAte, @NotBlank String ip, @NotBlank String userAgent) {
        this.destino = destino;
        this.validoAte = validoAte;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public UUID getId() {
        return id;
    }

}
