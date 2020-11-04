package br.com.zup.bootcamp.proposta.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    private byte[] fingerprint;

    @Deprecated
    public Biometria(){
    }

    public Biometria(@NotNull String fingerprint) {
        this.fingerprint = fingerprint.getBytes();
    }

    public byte[] getFingerprint() {
        return fingerprint;
    }

    public UUID getId() {
        return id;
    }
}
