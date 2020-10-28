package br.com.zup.bootcamp.proposta.annotations;

import br.com.zup.bootcamp.proposta.validators.CpfOuCnpjValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {CpfOuCnpjValidator.class})
@Target({ FIELD})
@Retention(RUNTIME)
public @interface CpfOuCnpj {
    String message() default "Documento inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName();

    Class<?> domainClass();
}
