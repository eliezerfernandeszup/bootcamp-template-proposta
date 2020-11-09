package br.com.zup.bootcamp.proposta.model.request;

import br.com.zup.bootcamp.proposta.model.Aviso;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoRequest {
    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate validoAte;

    public Aviso toAviso(HttpServletRequest request){
        return new Aviso(destino, validoAte, request.getRemoteAddr(), request.getHeader(HttpHeaders.USER_AGENT));
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(LocalDate validoAte) {
        this.validoAte = validoAte;
    }
}
