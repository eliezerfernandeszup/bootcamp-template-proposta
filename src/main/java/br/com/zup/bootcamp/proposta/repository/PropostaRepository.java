package br.com.zup.bootcamp.proposta.repository;

import br.com.zup.bootcamp.proposta.enums.PropostaStatus;
import br.com.zup.bootcamp.proposta.model.Proposta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, String> {

    List<Proposta> findByDocumento(String documento);

    List<Proposta> findByResultadoPropostaStatus(PropostaStatus propostaStatus);
}
