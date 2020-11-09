package br.com.zup.bootcamp.proposta.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.HttpHeaders;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotBlank
    private String ipDoCliente;

    @NotBlank
    private String userAgent;

    @CreationTimestamp
    private LocalDateTime instanteDoBloqueio;
    
    @Deprecated
    public Bloqueio(){
    }

    public Bloqueio(HttpServletRequest request) {
        this.ipDoCliente = request.getRemoteAddr();
        this.userAgent = request.getHeader(HttpHeaders.USER_AGENT);
    }

    public UUID getId() {
        return id;
    }
}
