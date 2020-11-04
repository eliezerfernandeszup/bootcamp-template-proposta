package br.com.zup.bootcamp.proposta.model.request;

import br.com.zup.bootcamp.proposta.model.Biometria;

import javax.validation.constraints.NotNull;

public class BiometriaRequest {

    @NotNull
    private String fingerprint;

    public Biometria toBiometria(){
        return new Biometria(this.fingerprint);
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }
}
