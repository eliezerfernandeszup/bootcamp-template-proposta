package br.com.zup.bootcamp.proposta.validators;

import br.com.zup.bootcamp.proposta.annotations.CpfOuCnpj;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.util.Assert;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class CpfOuCnpjValidator implements ConstraintValidator<CpfOuCnpj, String> {

    private String atribute;
    private Class<?> theClass;

    @Override
    public void initialize(CpfOuCnpj value) {
        atribute = value.fieldName();
        theClass = value.domainClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.initialize(null);

        CNPJValidator cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);

        return cpfValidator.isValid(value , null) || cnpjValidator.isValid(value, null);
    }
}
