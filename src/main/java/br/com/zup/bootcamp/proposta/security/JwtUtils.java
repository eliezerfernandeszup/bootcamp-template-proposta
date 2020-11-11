package br.com.zup.bootcamp.proposta.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

public class JwtUtils {

    public static String getUserEmail(){
        Map<String, Object> user = ( (Jwt) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()
        ).getClaims();

        return user.get("email").toString();
    }
}
