package br.com.zup.bootcamp.proposta.annotations;

import br.com.zup.bootcamp.proposta.validators.Base64Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {Base64Validator.class})
@Target({ FIELD})
@Retention(RUNTIME)
public @interface Base64 {
    String message() default "É necessário enviar em Base64";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}