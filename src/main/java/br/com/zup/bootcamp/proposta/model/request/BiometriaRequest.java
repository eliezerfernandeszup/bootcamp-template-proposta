package br.com.zup.bootcamp.proposta.model.request;

import br.com.zup.bootcamp.proposta.annotations.Base64;
import br.com.zup.bootcamp.proposta.model.Biometria;

import javax.validation.constraints.NotNull;

public class BiometriaRequest {

    @NotNull
    @Base64
    private String fingerprint;

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Biometria toBiometria(){
        return new Biometria(fingerprint);
    }
}
