package br.com.zup.bootcamp.proposta.repository;

import br.com.zup.bootcamp.proposta.model.Proposta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, String> {
}
