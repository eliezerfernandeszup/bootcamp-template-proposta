package br.com.zup.bootcamp.proposta.repository;

import br.com.zup.bootcamp.proposta.model.RecuperarSenha;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecuperarSenhaRepository extends CrudRepository<RecuperarSenha, String> {
}
